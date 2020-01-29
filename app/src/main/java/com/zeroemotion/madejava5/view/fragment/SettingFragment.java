package com.zeroemotion.madejava5.view.fragment;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.notification.DailyNotification;
import com.zeroemotion.madejava5.notification.ReleasedNotification;

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
    private ReleasedNotification releasedNotification = new ReleasedNotification();


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preferences);
        SwitchPreference switchDaily = findPreference(getString(R.string.daily_key));
        SwitchPreference switchReleased = findPreference(getString(R.string.released_key));

        switchDaily.setOnPreferenceChangeListener(this);
        switchReleased.setOnPreferenceChangeListener(this);
        findPreference(getString(R.string.LANGUAGEKEY)).setOnPreferenceClickListener(this);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean isOn = (boolean) newValue;
        assert getActivity() != null;

        if (key.equals(getString(R.string.daily_key))){
            if (isOn){
                dailyNotification.setDailyReminder(getActivity());
            } else dailyNotification.cancelDailyReminder(getActivity());
        }
        if (key.equals(getString(R.string.released_key))){
            if (isOn){
                releasedNotification.setReleasedReminder(getActivity());
            } else releasedNotification.cancelNotification(getActivity());
        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.LANGUAGEKEY))){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return false;
    }
}
