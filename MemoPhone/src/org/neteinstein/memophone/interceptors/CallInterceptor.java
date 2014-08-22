package org.neteinstein.memophone.interceptors;

import org.neteinstein.memophone.MemoPhone;
import org.neteinstein.util.SharedPreferencesManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;

public class CallInterceptor extends BroadcastReceiver {

	public String TAG = "CallInterceptor";

	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

			boolean interceptionEnabled = SharedPreferencesManager
					.getPreferenceBoolean(context,
							MemoPhone.INTERCEPTION_ENABLED, true);

			boolean isEmergencyNumber = PhoneNumberUtils.isEmergencyNumber(number);
			
			if (interceptionEnabled && !isEmergencyNumber && !MemoPhone.FROM_MEMOPHONE) {
				intent = new Intent(context, CallIntercepted.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("number", number);
				context.startActivity(intent);
				setResultData(null);
			} else if (MemoPhone.FROM_MEMOPHONE) {
				MemoPhone.FROM_MEMOPHONE = false;
			}

		}
	}
}
