package com.example.restaurantproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantproject.Coupon.Coupon;
import com.example.restaurantproject.Coupon.CouponAdapter;
import com.example.restaurantproject.Coupon.couponDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

/*Activity for the user to view their available couons & promotions
    **Coupons are meant to be used only at the restaurant before payment(i.e. not for deliveries) */
public class CouponActivity extends AppCompatActivity {
    private RecyclerView mCouponRec;
    private LinearLayoutManager linearLayoutManager;
    private couponDatabase couponDatabase;
    private Button mAddCoupon;
    private String mText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        mCouponRec = (RecyclerView) findViewById(R.id.recycler_coupon);
        mAddCoupon = (Button) findViewById(R.id.addCouponButton);
        auth = FirebaseAuth.getInstance();
        mText = "";

        // Creating & setting a linear layout manager & adapter for the RecyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCouponRec.setLayoutManager(linearLayoutManager);
        mCouponRec.setHasFixedSize(true);
        // Initializing the database & its adapter
        couponDatabase = new couponDatabase(this);
        final CouponAdapter adapter = new CouponAdapter(this,
                couponDatabase.getCoupons(auth.getCurrentUser().getUid()), couponDatabase);
        mCouponRec.setAdapter(adapter);

        // Button to add a coupon into the account
        mAddCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog is used here for the user to input voucher code
                AlertDialog.Builder builder = new AlertDialog.Builder(CouponActivity.this);
                // Set up the dialog's input view
                final EditText input = new EditText(CouponActivity.this);
                builder.setView(input);
                builder.setTitle(R.string.text_voucher_input);
                // Set up the buttons
                builder.setPositiveButton(getString(R.string.text_use), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mText = input.getText().toString();
                        if(mText.equals("12345")){
                            //Generate a 6-letter string as a code
                            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                            StringBuilder salt = new StringBuilder();
                            Random rnd = new Random();
                            while (salt.length() < 6) {
                                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                                salt.append(SALTCHARS.charAt(index));
                            }
                            //Insert the coupon into the database
                            couponDatabase.insertCoupon(new Coupon(auth.getCurrentUser().getUid(),
                                    "Example Discount", "50% off on Fried Chicken",
                                    "31-4-2019", salt.toString(), 3));
                            //Display text confirmation & update RecyclerView
                            Toast.makeText(CouponActivity.this, getString(R.string.text_voucher_pass),
                                    Toast.LENGTH_SHORT).show();
                            mCouponRec.setAdapter(new CouponAdapter(CouponActivity.this,
                                    couponDatabase.getCoupons(auth.getCurrentUser().getUid()), couponDatabase));
                        }
                        else { // Invalid voucher code
                            Toast.makeText(CouponActivity.this, getString(R.string.text_voucher_fail),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.text_back), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }
}
