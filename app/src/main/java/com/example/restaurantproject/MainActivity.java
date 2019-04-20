package com.example.restaurantproject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/*The main activity after logging in, access all functions from here*/
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogOut, btnFmenu, btnReserve, btnOrders, btnCoupons, btnSettings;
    private TextView textUsername;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TEST Email: t@m.com Pass: 12345678
        auth = FirebaseAuth.getInstance();
        textUsername = findViewById(R.id.text_username);
        btnFmenu = findViewById(R.id.fmenuButton);
        btnReserve = findViewById(R.id.reserveButton);
        btnOrders = findViewById(R.id.ordersButton);
        btnCoupons = findViewById(R.id.couponButton);
        btnSettings = findViewById(R.id.settingsButton);
        btnLogOut = findViewById(R.id.logoutButton);

        //Displays user's email (& user ID for quick debugging)
        textUsername.setText(auth.getCurrentUser().getEmail()
                +"\n(User ID: "+auth.getCurrentUser().getUid()+")");

        //Food menu button: press to view the food menu & make orders
        btnFmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodMenuActivity.class));
            }
        });
        //Reserve button: press to view available tables and make a reservation
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReserveActivity.class));
            }
        });
        //Orders button: press to view the current delivery status & past deliveries
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
            }
        });
        //Coupons & Privileges button: press to view the current coupons & available promotions
        btnCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CouponActivity.class));
            }
        });
        //Settings button: press to see or change the app's settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class)
                        , 1);
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

    //Used in SettingsActivity (requestCode = 1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the language has been changed in SettingsActivity, a RESULT_OK will be sent back
        // in order to also finish this activity and close the application
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) { finish(); }
        }
    }
}
