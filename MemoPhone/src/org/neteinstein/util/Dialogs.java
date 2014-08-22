package org.neteinstein.util;

import org.neteinstein.memophone.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Dialogs {

	public static void showAlertDialog(Context context, String title,
			String content, String buttonPositive) {
		showAlertDialog(context, title, content, buttonPositive, null, null,
				null, null, null);
	}

	public static void showAlertDialog(Context context, String title,
			String content, String buttonPositive, String buttonNegative,
			DialogInterface.OnClickListener buttonPositiveListener,
			DialogInterface.OnClickListener buttonNegativeListener,
			String buttonNeutral,
			DialogInterface.OnClickListener buttonNeutralListener) {

		// if (isVisible) {
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);

		if (title != null) {
			myAlertDialog.setTitle(title);
		}

		myAlertDialog.setMessage(content);

		if (buttonPositive != null
				|| (buttonPositive == null && buttonNegative == null)) {
			if (buttonPositiveListener == null) {
				buttonPositiveListener = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};
			}

			if (buttonPositive == null) {
				buttonPositive = context.getString(R.string.ok);
			}

			myAlertDialog.setPositiveButton(buttonPositive,
					buttonPositiveListener);
		}

		if (buttonNegative != null) {

			if (buttonNegativeListener == null) {
				buttonNegativeListener = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};
			}

			myAlertDialog.setNegativeButton(buttonNegative,
					buttonNegativeListener);
		}

		if (buttonNeutral != null) {
			if (buttonNeutralListener == null) {
				buttonNeutralListener = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};
			}

			myAlertDialog
					.setNeutralButton(buttonNeutral, buttonNeutralListener);
		}

		myAlertDialog.setCancelable(false);

		myAlertDialog.show();
		// }
	}
}
