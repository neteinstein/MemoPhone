package org.neteinstein.memophone.interceptors;

import java.lang.reflect.Method;
import java.util.Random;

import org.neteinstein.memophone.Main;
import org.neteinstein.memophone.MemoPhone;
import org.neteinstein.memophone.R;
import org.neteinstein.util.Contacts;
import org.neteinstein.util.Notifications;
import org.neteinstein.util.Toasts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CallIntercepted extends Activity {

	private final int android_os_Build_VERSION_CODES_ECLAIR = 5;

	private String contactName = "";
	private String contactNumber = null;
	private String numberStart = null;
	private String numberMissing = null;
	private String numberEnd = null;
	private boolean invalidNumber = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Notifications.showNotification(this);

		if (getIntent() != null && getIntent().getStringExtra("number") != null) {
			contactNumber = getIntent().getStringExtra("number");
		}

		setContentView(R.layout.guess_phone);

		// In theory this code can be commented
		// It is here as a safety measure if the interceptor fails in ending the
		// current call
		try {

			TelephonyManager tm = (TelephonyManager) this
					.getSystemService(Context.TELEPHONY_SERVICE);
			Class<?> clazz = Class.forName(tm.getClass().getName());
			Method m = clazz.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			com.android.internal.telephony.ITelephony it = (com.android.internal.telephony.ITelephony) m
					.invoke(tm);
			it.endCall();
		} catch (Exception e) {
			Log.d("CallIntercepted", "Something went wrong!", e);
		}

		randomNumber();
	}

	private void randomNumber() {
		Random randomGenerator = new Random();
		int pos = randomGenerator.nextInt(contactNumber.length());
		int attemps = 50;

		do {
			attemps--;

			try {

				Integer.parseInt("" + contactNumber.charAt(pos));

				if (pos > 0) {
					numberStart = contactNumber.substring(0, pos);
					TextView numberStartView = ((TextView) findViewById(R.id.phone_first_part));
					numberStartView.setText(numberStart);
					numberStartView.setVisibility(View.VISIBLE);
				}

				numberMissing = "" + contactNumber.charAt(pos);

				if (pos < contactNumber.length() - 1) {
					numberEnd = contactNumber.substring(pos + 1);
					TextView numberEndView = ((TextView) findViewById(R.id.phone_third_part));
					numberEndView.setText(numberEnd);
					numberEndView.setVisibility(View.VISIBLE);

				}

			} catch (Exception e) {

			}
		} while (numberMissing == null && attemps > 0);

		if (attemps == 0) {
			invalidNumber = true;
		} else {

			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
					Uri.encode(contactNumber));
			ContentResolver cr = getContentResolver();

			Cursor cursor = cr.query(uri, new String[] {
					PhoneLookup.DISPLAY_NAME, ContactsContract.Contacts._ID },
					null, null, null);

			if (cursor.moveToFirst()) {
				contactName = cursor.getString(cursor
						.getColumnIndex(PhoneLookup.DISPLAY_NAME));

				TextView contactNameView = ((TextView) findViewById(R.id.contact_name));
				contactNameView.setText(contactName);
				contactNameView.setVisibility(View.VISIBLE);

				ImageView photoView = (ImageView) findViewById(R.id.contact_photo);

				Uri photo = Contacts.getPhotoUri(this, cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID)));
				if (photo != null) {
					photoView.setImageURI(photo);
				} else {
					photoView.setImageResource(R.drawable.ic_contact_picture);
				}
			}

		}

	}

	@Override
	public void onResume() {
		super.onResume();

		if (invalidNumber) {
			matchSuccessful();
			return;
		}

	}

	public void match(View view) {
		String userInputNumber = ((EditText) findViewById(R.id.phone_second_part))
				.getText().toString();

		if (numberMissing.equalsIgnoreCase(userInputNumber)) {
			Toasts.showToast(this,
					getString(R.string.right_guess)
							.replace("%d", contactNumber));
			matchSuccessful();
		} else {
			Toasts.showToast(this, getString(R.string.wrong_guess));
		}
	}

	public void matchSuccessful() {
		MemoPhone.FROM_MEMOPHONE = true;

		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + contactNumber));
		startActivity(intent);
		finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (android.os.Build.VERSION.SDK_INT < android_os_Build_VERSION_CODES_ECLAIR
				&& keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {

			onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
		Intent intent = new Intent(this, Main.class);
		intent.putExtra(getString(R.string.page), 0);
		startActivity(intent);
	}
}
