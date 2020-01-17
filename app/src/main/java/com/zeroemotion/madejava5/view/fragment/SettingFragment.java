package com.zeroemotion.madejava5.view.fragment;


import android.os.Bundle;

import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.notification.DailyNotification;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public static final CharSequence CHANNEL_NAME = "movie channel";
    private DailyNotification dailyNotification = new DailyNotification();


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preferences);
        SwitchPreference switchDaily = findPreference(getString(R.string.daily_key));
        SwitchPreference switchReleased = findPreference(getString(R.string.released_key));

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
