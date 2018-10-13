package ua.yurezcv.stoic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import java.util.Calendar;

import ua.yurezcv.stoic.utils.notifications.AlarmsHelper;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            setHasOptionsMenu(true);

            /*
             * A preference value change listener that updates the preference's summary
             * to reflect its new value.
             */
            Preference.OnPreferenceChangeListener listChangeListener = new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    String stringValue = value.toString();
                    if (preference instanceof ListPreference) {
                        // For list preferences, look up the correct display value in
                        // the preference's 'entries' list.
                        ListPreference listPreference = (ListPreference) preference;
                        int index = listPreference.findIndexOfValue(stringValue);

                        // Set the summary to reflect the new value.
                        preference.setSummary(
                                index >= 0
                                        ? listPreference.getEntries()[index]
                                        : null);

                        // update time/interval if it was changed
                        enableNotifications(true);
                    }
                    return true;
                }
            };

            // bind the listener to both List Preferences
            bindPreferenceListener(getString(R.string.pref_key_notification_time), listChangeListener);
            bindPreferenceListener(getString(R.string.pref_key_notification_freq), listChangeListener);

            // bind another listener for the toggle preference
            Preference notificationToggle = findPreference(getString(R.string.pref_key_enable_notifications));
            notificationToggle.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    enableNotifications((boolean)newValue);
                    return true;
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        void bindPreferenceListener(String key, Preference.OnPreferenceChangeListener listener) {
            Preference preference = findPreference(key);
            String value = PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext())
                    .getString(preference.getKey(), "");
            preference.setOnPreferenceChangeListener(listener);
            listener.onPreferenceChange(preference, value);
        }

        public void enableNotifications(boolean isEnabled) {
            if(isEnabled) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String timeValue = preferences.getString(
                        getString(R.string.pref_key_notification_time),
                        getString(R.string.pref_default_notification_time)
                );

                String[] splitTime = timeValue.split(":");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
                long time = calendar.getTimeInMillis();

                String intervalValue = preferences.getString(
                        getString(R.string.pref_key_notification_freq),
                        getString(R.string.pref_default_notification_freq));
                long interval = Long.parseLong(intervalValue);

                AlarmsHelper.scheduleRepeatingAlarm(getActivity().getApplicationContext(), time, interval);
            } else {
                AlarmsHelper.cancelAlarm(getActivity().getApplicationContext());
            }
        }
    }
}
