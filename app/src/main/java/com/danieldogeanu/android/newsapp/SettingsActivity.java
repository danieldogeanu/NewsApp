package com.danieldogeanu.android.newsapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class that initializes the Settings Activity for the app.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Overrides the onCreate() method to assemble and display the Settings Activity.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * Inner class that extends the PreferenceFragment and implements a Change Listener for the Preference class.
     */
    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        /**
         * Overrides the onCreate() method to assemble and display the NewsPreferenceFragment.
         * @param savedInstanceState The saved instance state.
         */
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Get and bind the selected section as a preference.
            Preference selectedSection = findPreference(getString(R.string.settings_section_key));
            bindPreferenceSummaryToValue(selectedSection);

            // Get and bind the page size as a preference.
            Preference pageSize = findPreference(getString(R.string.settings_page_size_key));
            bindPreferenceSummaryToValue(pageSize);

            // Get and bind the number of pages as a preference.
            Preference numberOfPages = findPreference(getString(R.string.settings_pages_key));
            bindPreferenceSummaryToValue(numberOfPages);
        }

        /**
         * Override the onPreferenceChange() method in order to get the new values,
         * save them, and change the app according to them.
         * @param preference The preference that changed.
         * @param value The new value of the preference.
         * @return Returns true if the preferences have changed.
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            // Extract the String value from the Object.
            String stringValue = value.toString();

            // Check to see if it's a list or a text field value and process accordingly.
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }

            return true;
        }

        /**
         * Method to bind the preferences to their values.
         * @param preference The preference to bind.
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }

}
