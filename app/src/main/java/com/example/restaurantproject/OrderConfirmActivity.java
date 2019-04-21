package com.example.restaurantproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantproject.Order.Order;
import com.example.restaurantproject.Order.OrderDatabase;
import com.google.firebase.auth.FirebaseAuth;

/*This activity was created for input the information of order e.g. Address,Phone,Amount
    - Click Confirm Button to send data to Order Database*/

public class OrderConfirmActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView FoodName;
    private EditText inputAmount, inputAddress, inputPhone;
    private Button btnconfirm;
    private String food,uID;
    private int price;
    private OrderDatabase orderDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        setTitle(R.string.title_order_confirm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Call database of order
        orderDB = new OrderDatabase(this);
        //Set Food Name in the top of page
        FoodName = findViewById(R.id.food_name);
        FoodName.setText(getIntent().getStringExtra("food"));
        //Get data from the EditText and getuID from Firebase
        auth = FirebaseAuth.getInstance();
        inputAmount = findViewById(R.id.input_amount);
        inputAddress = findViewById(R.id.input_adress);
        inputPhone = findViewById(R.id.input_phone);
        btnconfirm = findViewById(R.id.comfirmbtn);

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Food Name and Price from intent from FoodMenuActivity
                food = getIntent().getStringExtra("food");
                //Get User ID from Firebase
                uID = auth.getCurrentUser().getUid();
                price = getIntent().getIntExtra("price",0);
                final int amount = Integer.parseInt(inputAmount.getText().toString());
                final String address = inputAddress.getText().toString();
                final String phone = inputPhone.getText().toString();
                //Create order object to contain data, then insert the order object to orderDB
                Order order = new Order(uID,food,amount,price*amount,address+", "+phone,false);
                Toast.makeText(OrderConfirmActivity.this, getString(R.string.text_order_added),
                        Toast.LENGTH_SHORT).show();
                //Log.d("SUCCESS",order.getPrice()+" baht");
                //Insert the order object into the database.
                orderDB.insertOrder(order);
                //When the order was inserted in the database, it will change to FoodMenu to
                // let the user make a decision to continue shopping or stop.
                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
