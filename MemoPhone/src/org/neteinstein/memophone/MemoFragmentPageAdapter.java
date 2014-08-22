package org.neteinstein.memophone;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;

public class MemoFragmentPageAdapter extends FragmentPagerAdapter implements
		TitleProvider {

	List<Fragment> fragments = null;
	List<String> titles = null;

	public MemoFragmentPageAdapter(FragmentManager fm,
			List<Fragment> fragments, List<String> titles) {
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);

	}

	public String getTitle(int position) {
		return titles.get(position);
	}

}
