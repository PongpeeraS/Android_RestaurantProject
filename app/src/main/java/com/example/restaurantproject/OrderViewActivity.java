package com.example.restaurantproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantproject.Order.OrderDatabase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/*This class was created for viewing the order information
* - name
* - price
* - userID
* - location
* - receive button that use to accept that the user receive food or product from the shop.
* So, it will change the status of the order and display in the history tab instead.*/

public class OrderViewActivity extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private GoogleMap gmap;
    private TextView title,price,amount,address;
    private Boolean isReceived;
    private Button receivebtn;
    private OrderDatabase orderDatabase;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        setTitle(R.string.title_order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderDatabase = new OrderDatabase(this);
        //Set text view to display the information of the order
        title = findViewById(R.id.order_name);
        price = findViewById(R.id.order_price);
        amount = findViewById(R.id.order_amount);
        address = findViewById(R.id.order_address);
        //The receive button
        receivebtn = findViewById(R.id.recievebtn);

        title.setText(getIntent().getStringExtra("oname"));
        price.setText(getString(R.string.label_total_price)
                +"\n" + getIntent().getIntExtra("oprice",0)+" "+getString(R.string.text_baht));
        amount.setText(getString((R.string.label_amount))
                +"\n" + getIntent().getIntExtra("oamount", 0));
        address.setText(getString(R.string.label_address)
                +"\n" + getIntent().getStringExtra("oaddress"));

        //Map initialization
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        //Define space to display a map
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        //Check the status of the order
        isReceived = getIntent().getBooleanExtra("status",false);
        if(isReceived){
            //If the order was received, the received button must not display.
            mapView.setVisibility(View.INVISIBLE);
            receivebtn.setVisibility(View.INVISIBLE);
        }

        //Click receive button to call UpdateStatusOrder function
        receivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Food Name and Price from intent from FoodMenuActivity.
                //Then send it to UpdateStatusOrder function.
                //UpdateStatusOrder function was created for changing the status of the order.
                orderDatabase.UpdateStatusOrder(getIntent().getStringExtra("uID"),getIntent().getStringExtra("oname"));
                //When the order was updated the system will return to OrderActivity page.
                startActivity(new Intent(OrderViewActivity.this, OrderActivity.class));
                finish();
            }
        });
    }

    /*
    * The below parts is about GoogleMap setting.
    * */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        //The location that show in the map by using Latitude and Longitude to indicate the location.
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        //Marking the location
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
    //onSupportNavigateUp & onBackPressed: restart parent activity as it was closed  by the adapter for refreshing.
    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(OrderViewActivity.this, OrderActivity.class));
        finish();
        return true;
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(OrderViewActivity.this, OrderActivity.class));
        finish();
    }
}