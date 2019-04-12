package com.example.restaurantproject;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/*The main activity after logging in, access all functions from here*/
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogOut, btnFmenu, btnReserve, btnOrdering, btnCoupon, btnSettings;
    private TextView textUsername;
    private ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TEST Email: t@m.com Pass: 12345678
        auth = FirebaseAuth.getInstance();
        textUsername = findViewById(R.id.text_username);
        userPic = findViewById(R.id.userPicView);
        btnFmenu = findViewById(R.id.fmenuButton);
        btnReserve = findViewById(R.id.reserveButton);
        btnOrdering = findViewById(R.id.orderingButton);
        btnCoupon = findViewById(R.id.couponButton);
        btnSettings = findViewById(R.id.settingsButton);
        btnLogOut = findViewById(R.id.logoutButton);

        //Displays brief user information
        textUsername.setText(auth.getCurrentUser().getEmail()); //get email to display here
        //userPic.setImageURI(auth.getCurrentUser().getPhotoUrl()); //HOW TO GET USER PROFILE PICS?

        //Food menu button: press to view the food menu & make orders
        btnFmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodMenuActivity.class));
            }
        });
        //Settings button: press to see or change the app's settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        //Log out button: press to sign out and return to login screen
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Dialog is used here for users to confirm logout*/
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(getString(R.string.logout_confirm));
                builder.setPositiveButton(getString(R.string.text_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        auth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton(getString(R.string.text_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.show();
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
