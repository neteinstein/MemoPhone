package org.neteinstein.util;

import org.neteinstein.memophone.Main;
import org.neteinstein.memophone.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notifications {
	private static final int MEMOPHONE_NOTIFICATION_ID = 1;

	public static void showNotification(Context activityContext) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notificationManager = (NotificationManager) activityContext
				.getSystemService(ns);

		int icon = R.drawable.icon; // icon from resources
		CharSequence tickerText = "MemoPhone is active!"; // ticker-text
		long when = System.currentTimeMillis(); // notification time
		Context context = activityContext.getApplicationContext(); // application
																	// Context
		CharSequence contentTitle = "MemoPhone is active!"; // expanded message
															// title
		CharSequence contentText = "To disable press here."; // expanded message
																// text

		Intent notificationIntent = new Intent(activityContext, Main.class);
		notificationIntent
				.putExtra(activityContext.getString(R.string.page), 0);
		PendingIntent contentIntent = PendingIntent.getActivity(
				activityContext, 0, notificationIntent, 0);

		// the next two lines initialize the Notification, using the
		// configurations above
		Notification notification = new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		notification.flags |= Notification.DEFAULT_VIBRATE
				| Notification.FLAG_ONGOING_EVENT;

		notificationManager.notify(MEMOPHONE_NOTIFICATION_ID, notification);
	}

	public static void dismissNotification(Context activityContext) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notificationManager = (NotificationManager) activityContext
				.getSystemService(ns);
		notificationManager.cancel(MEMOPHONE_NOTIFICATION_ID);
	}

}
