package com.example.restaurantproject;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/*Activity for user to change the app's settings*/
public class SettingsActivity extends AppCompatActivity {

    /*Initialize & set title*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new LanguagePreferenceFragment()).commit();
        setTitle(R.string.btn_settings);
        /*In every activity other than Main, Login & Signup, the action bar will contain
        * a back button on the top-left corner to go back to the parent activity.*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*Fragment of the activity to change the app's language*/
    public static class LanguagePreferenceFragment extends PreferenceFragment {
        ListPreference listPreference;
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            //Getting preference & array
            listPreference = (ListPreference) findPreference("pref_language");
            final String[] langArr = getResources().getStringArray(R.array.language_option);

            //Setting default value & summary of the preference bar according to current language
            if(Locale.getDefault().getLanguage().equals("th")){
                listPreference.setValueIndex(1);
                listPreference.setSummary(langArr[1]);
            }
            else if(Locale.getDefault().getLanguage().equals("en")){
                listPreference.setValueIndex(0);
                listPreference.setSummary(langArr[0]);
            }

            // After selecting a new language, the app will change its locale and close itself
            listPreference.setOnPreferenceChangeListener(new ListPreference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    //newValue = language code (i.e. "th" or "en") used in setting locale
                    if(newValue.toString().equals("th")) preference.setSummary(langArr[1]);
                    else if(newValue.toString().equals("en")) preference.setSummary(langArr[0]);
                    String langCode = newValue.toString();
                    //If the selected language is not the same as the default, change to new language
                    if(!langCode.equals(Locale.getDefault().getLanguage())) {
                        Locale locale = new Locale(langCode);//Set Selected Locale
                        Locale.setDefault(locale);//set new locale as default
                        Configuration config = new Configuration();//get Configuration
                        config.locale = locale;//set config locale as selected locale
                        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

                        //Create an AlertDialog to tell the user that the app will close
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.text_lang_changed)
                                .setCancelable(false)
                                .setPositiveButton(R.string.text_back, new DialogInterface.OnClickListener() {
                                    //After closing the dialog, send the result back to MainActivity to close it
                                    //and also close this activity (SettingsActivity)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        getActivity().setResult(RESULT_OK, null);
                                        getActivity().finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    return true;
                }
            });
        }
    }
    //On pressing back button, return to parent activity
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
