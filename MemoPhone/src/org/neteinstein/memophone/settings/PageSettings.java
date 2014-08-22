package org.neteinstein.memophone.settings;

import org.neteinstein.memophone.MemoPhone;
import org.neteinstein.memophone.R;
import org.neteinstein.util.SharedPreferencesManager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.ToggleButton;

public class PageSettings extends Fragment {

	public final String TAG = this.getClass().getCanonicalName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_settings, container, false);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		boolean isEnabled = SharedPreferencesManager.getPreferenceBoolean(
				getActivity(), MemoPhone.INTERCEPTION_ENABLED, true);

		ToggleButton tg = (ToggleButton) getActivity().findViewById(
				R.id.interception_enabled);
		tg.setChecked(isEnabled);

		populateContactList();
	}

	private void populateContactList() {
		// Build adapter with contact entries
		Cursor cursor = getContacts();
		String[] fields = new String[] { ContactsContract.Data.DISPLAY_NAME };
		CursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.contact_entry, cursor, fields,
				new int[] { R.id.contactEntryName });
		ListView mContactList = (ListView) getActivity().findViewById(
				R.id.contact_list);
		mContactList.setAdapter(adapter);
	}

	/**
	 * Obtains the contact list for the currently selected account.
	 * 
	 * @return A cursor for for accessing the contact list.
	 */
	private Cursor getContacts() {
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		return getActivity().managedQuery(uri, projection, null,
				selectionArgs, sortOrder);
	}

}