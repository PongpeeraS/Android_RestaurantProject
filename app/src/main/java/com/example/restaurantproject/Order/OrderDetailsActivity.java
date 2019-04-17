package com.example.restaurantproject.Order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.restaurantproject.R;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView mOname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Bundle extras = getIntent().getExtras();
        mOname = findViewById(R.id.ordername);
        mOname.setText(extras.getString("oname"));
    }
}
