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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    Button btnLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnLanguage = findViewById(R.id.btn_language);

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.text_language).setItems(R.array.language_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        switch (i) {
                            case 0:
                                editor.putString("language", "en");
                                editor.commit();
                                Toast.makeText(SettingsActivity.this, "English Selected", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                editor.putString("language", "th");
                                editor.commit();
                                Toast.makeText(SettingsActivity.this, "Thai Selected", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        finishAndRemoveTask();
                    }
                }).setCancelable(false).create().show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
        String languageToLoad = sharedPref.getString("language", "");
        Locale locale = new Locale(languageToLoad);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
    }
}
