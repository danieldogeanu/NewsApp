package com.danieldogeanu.android.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

        // Limits for the numbers of Articles per Page.
        private static final int MIN_ARTICLES = 1;
        private static final int MAX_ARTICLES = 200;

        // Limits for the number of Pages allowed.
        private static final int MIN_PAGES = 1;
        private static final int MAX_PAGES = 256;

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
            String stringValue = value.toString();
            String prefKey = preference.getKey();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                boolean canSave = canSavePref(prefKey, stringValue);
                if (canSave) preference.setSummary(stringValue);
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

        /**
         * Method to check if the value is safe to save, so that the app wont crash.
         * @param prefKey The Preference Key to check the value for.
         * @param prefValue The Value to check if it's safe to save.
         * @return Returns true if it's safe to safe, or false otherwise.
         */
        private boolean canSavePref(String prefKey, String prefValue) {
            String pageSizeKey = getString(R.string.settings_page_size_key);
            String numberOfPagesKey = getString(R.string.settings_pages_key);
            String pageSizeError = getString(R.string.settings_page_size_error);
            String numberOfPagesError = getString(R.string.settings_pages_error);
            int currentPrefValue = Integer.parseInt(prefValue);
            boolean canSave = false;

            if (prefKey.equals(pageSizeKey)) {
                if ((currentPrefValue >= MIN_ARTICLES) && (currentPrefValue <= MAX_ARTICLES)) {
                    canSave = true;
                } else {
                    Utils.showToast(getActivity(), pageSizeError);
                }
            }

            if (prefKey.equals(numberOfPagesKey)) {
                if ((currentPrefValue >= MIN_PAGES) && (currentPrefValue <= MAX_PAGES)) {
                    canSave = true;
                } else {
                    Utils.showToast(getActivity(), numberOfPagesError);
                }
            }

            return canSave;
        }
    }

}
