package com.example.restaurantproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/*Activity for user to change the app's settings*/
public class SettingsActivity extends AppCompatActivity {
    Button btnLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnLanguage = findViewById(R.id.btn_language);

        /*Change language button: change the language between English & Thai
        * A dialog box will appear after pressing the button, where the user can choose
        * the app's language. If the language chosen is different than the current one,
        * the app will save the locale value in SharedPreferences and restart the app with
        * the new locale settings.*/
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.text_language).setItems(R.array.language_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //SharedPreference is used here to keep the locale
                        SharedPreferences sharedPreferences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        switch (i) {
                            case 0:
                                editor.putString("language", "en");
                                editor.commit();
                                Toast.makeText(SettingsActivity.this, "Restarting app", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                editor.putString("language", "th");
                                editor.commit();
                                Toast.makeText(SettingsActivity.this, "Restarting app", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        //Restarts the application with the new locale
                        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis()
                                + 1000, PendingIntent.getActivity(getBaseContext(), 0,
                                new Intent(getIntent()), getIntent().getFlags()));
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }).setCancelable(true).create().show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Setting the activity's locale through saved SharedPreference*/
        SharedPreferences sharedPref = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
        String languageToLoad = sharedPref.getString("language", "");
        Locale locale = new Locale(languageToLoad);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
    }
}
