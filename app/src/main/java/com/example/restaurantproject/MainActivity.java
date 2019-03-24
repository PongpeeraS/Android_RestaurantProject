package com.example.restaurantproject;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogOut, btnFmenu, btnReserve, btnHistory, btnCoupon, btnPrefs;
    private TextView textUsername;
    private ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUsername = findViewById(R.id.text_username);
        userPic = findViewById(R.id.userPicView);
        btnFmenu = findViewById(R.id.fmenuButton);
        btnReserve = findViewById(R.id.reserveButton);
        btnHistory = findViewById(R.id.historyButton);
        btnCoupon = findViewById(R.id.couponButton);
        btnPrefs = findViewById(R.id.prefButton);
        btnLogOut = findViewById(R.id.logoutButton);

        auth = FirebaseAuth.getInstance();
        textUsername.setText(auth.getCurrentUser().getDisplayName()); //get username to display here
        //userPic.setImageURI(auth.getCurrentUser().getPhotoUrl()); //HOW TO GET USER PROFILE PICS?

        //Preferences button: press to see the app's settings.
        btnPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
            }
        });
        //Log out button: press to sign out and return to login screen.
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
