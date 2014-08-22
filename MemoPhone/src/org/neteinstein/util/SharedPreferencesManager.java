package org.neteinstein.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

	public static final String TAG = SharedPreferencesManager.class.getClass()
			.getCanonicalName();

	private static final String preferencesFile = "memoPhone";

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(preferencesFile,
				Activity.MODE_PRIVATE);
	}

	public static void storePreferenceString(Context context, String name,
			String toStore) {

		SharedPreferences settings = getPreferences(context);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString(name, toStore);

		editor.commit();
	}

	public static void storePreferenceBoolean(Context context, String name,
			boolean toStore) {

		SharedPreferences settings = getPreferences(context);
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean(name, toStore);

		editor.commit();
	}

	public static void storePreferenceInt(Context context, String name,
			int toStore) {

		SharedPreferences settings = getPreferences(context);
		SharedPreferences.Editor editor = settings.edit();

		editor.putInt(name, toStore);

		editor.commit();
	}

	public static String getPreferenceString(Context context, String name,
			String defaultString) {

		SharedPreferences settings = getPreferences(context);

		return settings.getString(name, defaultString);
	}

	public static boolean getPreferenceBoolean(Context context, String name,
			boolean defaultBoolean) {

		SharedPreferences settings = getPreferences(context);

		return settings.getBoolean(name, defaultBoolean);
	}

	public static int getPreferenceInt(Context context, String name,
			int defaultInt) {

		SharedPreferences settings = getPreferences(context);

		return settings.getInt(name, defaultInt);
	}

	public static void removePreference(Context context, String name) {
		SharedPreferences settings = getPreferences(context);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(name);
		editor.commit();
	}

}
