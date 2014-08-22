package org.neteinstein.memophone.game;

import java.util.Random;

import org.neteinstein.memophone.R;
import org.neteinstein.memophone.dtos.MemoContact;
import org.neteinstein.util.Contacts;
import org.neteinstein.util.Toasts;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Game extends Activity {

	private String TAG = "Game";

	private MemoContact contact = null;
	private String contactName = "";
	private String contactNumber = null;
	private String numberStart = null;
	private String numberMissing = null;
	private String numberEnd = null;
	private boolean invalidNumber = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		contactNumber = getRandomContact();
		if (contactNumber != null) {
			setContentView(R.layout.guess_phone);
			randomNumber();
			if (invalidNumber) {
				Log.d(TAG,
						"If number is not valid should randomize for X times before failing...");
			}
		} else {
			setContentView(R.layout.guess_phone_no_contacts);
		}
	}

	private String getRandomContact() {
		int maxTries = 50;
		String phoneNumber = null;

		ContentResolver cr = getContentResolver();

		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				new String[] { PhoneLookup.DISPLAY_NAME,
						PhoneLookup.HAS_PHONE_NUMBER,
						ContactsContract.Contacts._ID }, null, null, null);

		int size = cursor.getCount();

		if (size == 0) {
			return phoneNumber;
		}

		boolean found = false;
		Random rnd = new Random();
		while (!found && maxTries > 0) {
			int index = rnd.nextInt(size);
			if (index > 1) {
				cursor.moveToPosition(index);
			} else {
				cursor.moveToFirst();
			}

			found = cursor.getInt(cursor
					.getColumnIndex(PhoneLookup.HAS_PHONE_NUMBER)) == 1;
			if (found) {
				contact = new MemoContact();
				contact.setName(cursor.getString(cursor
						.getColumnIndex(PhoneLookup.DISPLAY_NAME)));

				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = "
								+ cursor.getString(cursor
										.getColumnIndex(PhoneLookup._ID)),
						null, null);
				if (phones.moveToNext()) {
					phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					Log.d("Phone found:", phoneNumber);
				}
				phones.close();

			}
			maxTries--;
		}
		return phoneNumber;
	}

	private void randomNumber() {
		Random randomGenerator = new Random();
		int pos = randomGenerator.nextInt(contactNumber.length());
		int attemps = 50;

		EditText numberMissingView = ((EditText) findViewById(R.id.phone_second_part));
		numberMissingView.setText("");

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
		contactName = "";
		numberStart = "";
		numberMissing = "";
		numberEnd = "";
		contactNumber = getRandomContact();
		if (contactNumber != null) {
			randomNumber();
		} else {
			Log.d(TAG, "You have no contacts");
		}
	}
}
