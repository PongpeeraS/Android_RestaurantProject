package com.example.restaurantproject;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinLanguage = findViewById(R.id.spinner_language);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(SettingsActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.language_option));
        spinLanguage.setAdapter(mAdapter);

        String currentLocale = Locale.getDefault().getLanguage();
        if (currentLocale.equals("th")) spinLanguage.setSelection(mAdapter.getPosition("ไทย"));
        else spinLanguage.setSelection(mAdapter.getPosition("English"));

        spinLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = SettingsActivity.this;
                Locale locale;
                if (i == 1) locale = new Locale("th");
                else locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration configuration = context.getResources().getConfiguration();
                configuration.setLocale(locale);
                configuration.setLayoutDirection(locale);
                context.createConfigurationContext(configuration);
                System.out.println("it's here");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
