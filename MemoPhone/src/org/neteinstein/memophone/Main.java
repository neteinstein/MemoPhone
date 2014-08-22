package org.neteinstein.memophone;

import java.util.ArrayList;
import java.util.List;

import org.neteinstein.memophone.about.PageAbout;
import org.neteinstein.memophone.game.PageGame;
import org.neteinstein.memophone.settings.PageSettings;
import org.neteinstein.memophone.start.PageStart;
import org.neteinstein.util.Notifications;
import org.neteinstein.util.SharedPreferencesManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class Main extends FragmentActivity {

	public String TAG = this.getClass().getSimpleName().toString();

	private int startPage = 1;

	private ViewPager viewPager;
	private MemoFragmentPageAdapter pageAdaptor;
	private TitlePageIndicator indicator;

	ArrayList<String> fragmentTitles = new ArrayList<String>();
	ArrayList<Class<?>> fragmentClasses = new ArrayList<Class<?>>(); 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewpager);

		fragmentTitles.add(getString(R.string.PAGE_SETTINGS));
		fragmentClasses.add(PageSettings.class);
		
		fragmentTitles.add(getString(R.string.PAGE_START));
		fragmentClasses.add(PageStart.class);
		
		fragmentTitles.add(getString(R.string.PAGE_GAME));
		fragmentClasses.add(PageGame.class);
		
		fragmentTitles.add(getString(R.string.PAGE_ABOUT));
		fragmentClasses.add(PageAbout.class);
		

		List<Fragment> fragments = new ArrayList<Fragment>();
		for (int i = 0; i < fragmentClasses.size(); i++) {
			fragments.add(Fragment.instantiate(this,
					fragmentClasses.get(i).getName()));
		}

		pageAdaptor = new MemoFragmentPageAdapter(getSupportFragmentManager(),
				fragments, fragmentTitles);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(pageAdaptor);
		viewPager.setCurrentItem(1);

		indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
		indicator.setCurrentItem(startPage);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Bundle extras = intent.getExtras();
		if (extras != null) {
			startPage = extras.getInt(getString(R.string.page), -1);
			if (startPage >= 0) {
				indicator.setCurrentItem(startPage);
			}
		}
	}

	public void onClickInterception(View v) {

		Log.d(TAG,
				""
						+ getSupportFragmentManager().findFragmentByTag(
								fragmentTitles.get(0)));

		ToggleButton tg = (ToggleButton) v;

		SharedPreferencesManager.storePreferenceBoolean(this,
				MemoPhone.INTERCEPTION_ENABLED, tg.isChecked());

		notification(tg.isChecked());

	}

	public void onClickInterceptionSelector(View v) {

		ToggleButton tg = (ToggleButton) v;
		if (tg.isChecked()) {
			findViewById(R.id.specific_contacts).setVisibility(View.GONE);
		} else {
			findViewById(R.id.specific_contacts).setVisibility(View.VISIBLE);
		}
	}

	public void notification(boolean isEnabled) {
		if (isEnabled) {
			Notifications.showNotification(this);
		} else {
			Notifications.dismissNotification(this);
		}
	}

	public void onClick(View view) {
		switch (Constants.getTabIndex(this, (String) view.getTag())) {
		case Constants.PAGE_SETTINGS_INDEX:
			Log.d(TAG, "No click for this fragment...");
			break;

		case Constants.PAGE_START_INDEX:
			Log.d(TAG, "No click for this fragment...");
			break;
		case Constants.PAGE_GAME_INDEX:
			((PageGame) pageAdaptor.getItem(Constants.PAGE_GAME_INDEX))
					.onClick(view);
			break;

		case Constants.PAGE_ABOUT_INDEX:
			Log.d(TAG, "No click for this fragment...");
			break;
		}

	}

}
