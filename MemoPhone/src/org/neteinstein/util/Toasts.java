package org.neteinstein.util;

import android.content.Context;
import android.widget.Toast;

public class Toasts {

	public static void showToast(Context context, String text){
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
