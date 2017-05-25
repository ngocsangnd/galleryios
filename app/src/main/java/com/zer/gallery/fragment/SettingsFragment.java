package com.zer.gallery.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.zer.gallery.R;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
