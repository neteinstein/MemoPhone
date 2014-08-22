package org.neteinstein.memophone;

import android.content.Context;

public class Constants {

	public final static int PAGE_SETTINGS_INDEX = 0;
	public final static int PAGE_START_INDEX = 1;
	public final static int PAGE_GAME_INDEX = 2;
	public final static int PAGE_ABOUT_INDEX = 3;

	public static int getTabIndex(Context context, String tabName) {
		if (context.getString(R.string.PAGE_SETTINGS).equalsIgnoreCase(tabName)) {
			return PAGE_SETTINGS_INDEX;
		} else if (context.getString(R.string.PAGE_START).equalsIgnoreCase(
				tabName)) {
			return PAGE_START_INDEX;
		} else if (context.getString(R.string.PAGE_GAME).equalsIgnoreCase(
				tabName)) {
			return PAGE_GAME_INDEX;
		} else if (context.getString(R.string.PAGE_ABOUT).equalsIgnoreCase(
				tabName)) {
			return PAGE_ABOUT_INDEX;
		}

		return -1;
	}

}
