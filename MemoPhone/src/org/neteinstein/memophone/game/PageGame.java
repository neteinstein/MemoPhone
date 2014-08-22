package org.neteinstein.memophone.game;

import org.neteinstein.memophone.MemoPhone;
import org.neteinstein.memophone.R;
import org.neteinstein.util.SharedPreferencesManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class PageGame extends Fragment {

	public String TAG = "PageGame";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.page_game_soon, container, false);

//		((Spinner) view.findViewById(R.id.memorization_types))
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					public void onItemSelected(AdapterView<?> parentView,
//							View selectedItemView, int position, long id) {
//						SharedPreferencesManager.storePreferenceInt(
//								getActivity(), MemoPhone.MEMORIZATION_TYPE,
//								position);
//					}
//
//					public void onNothingSelected(AdapterView<?> parentView) {
//						Log.d(TAG,
//								"Something is wrong, tax mode selected no option...");
//					}
//				});

		return view;

	}

	@Override
	public void onResume() {
		super.onResume();
//		int memorization_type_selected = SharedPreferencesManager
//				.getPreferenceInt(getActivity(), MemoPhone.MEMORIZATION_TYPE, 0);
//
//		Spinner memorizationType = (Spinner) getActivity().findViewById(
//				R.id.memorization_types);
//		memorizationType.setSelection(memorization_type_selected);
	}

	public void onClick(View view) {
		getActivity().startActivity(new Intent(this.getActivity(), Game.class));
	}
}
