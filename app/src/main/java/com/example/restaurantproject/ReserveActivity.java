package com.example.restaurantproject;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.restaurantproject.Reserve.ReserveDatabase;
import com.google.firebase.auth.FirebaseAuth;

/*This activity is used to view and reserve tables*/
public class ReserveActivity extends AppCompatActivity {
    //declare variables
    private Button T1, T2, T3, T4, T5, T6;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public static String name;
    public static int id;
    ReserveDatabase reserveDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set variable
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        setTitle(getString(R.string.btn_reserve));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        T1 = findViewById(R.id.Table1); T2 = findViewById(R.id.Table2);
        T3 = findViewById(R.id.Table3); T4 = findViewById(R.id.Table4);
        T5 = findViewById(R.id.Table5); T6 = findViewById(R.id.Table6);
        reserveDatabase = new ReserveDatabase(this);

        // Check if someone reserve this table
        Cursor res1 = reserveDatabase.getTable("Table_1");
        if(res1.getCount() > 0) {
           //Check if this user reserve this table
            Cursor email1 = reserveDatabase.getEmail("Table_1");
           email1.moveToNext();
            if (auth.getCurrentUser().getEmail().equals(email1.getString(0)) ) {
                //Change button's name to modify if this user reserve it
                T1.setText(getString(R.string.text_modify));
            }
            else {
                //disable button if this user did not reserve this
                T1.setText(getString(R.string.text_reserved));
                T1.setEnabled(false);
            }
        }

        //Set button
        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "Table_1";
                id = 1;
                //Check if it is free to reserve
                if(T1.getText().equals(getString(R.string.text_reserve))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveDetailsActivity.class), 1);
                }
                //Go to modify if this user reserve once
                else if(T1.getText().equals(getString(R.string.text_modify))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveModifyActivity.class), 1);
                }
            }
        });
        // Check if someone reserve this table
        Cursor res2 = reserveDatabase.getTable("Table_2");
        if(res2.getCount() > 0) {
            //Check if this user reserve this table
            Cursor email1 = reserveDatabase.getEmail("Table_2");
            email1.moveToNext();
            if (auth.getCurrentUser().getEmail().equals(email1.getString(0)) ) {
                //Change button's name to modify if this user reserve it
                T2.setText(getString(R.string.text_modify));
            }
            else {
                //disable button if this user did not reserve this
                T2.setEnabled(false);
                T2.setText(getString(R.string.text_reserved));
            }
        }
        T2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                name = "Table_2";
                id = 2;
                //Check if it is free to reserve
                if(T2.getText().equals(getString(R.string.text_reserve))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveDetailsActivity.class), 1);
                }
                //Go to modify if this user reserve once
                else if(T2.getText().equals(getString(R.string.text_modify))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveModifyActivity.class), 1);
                }

            }
        });
        // Check if someone reserve this table
        Cursor res3 = reserveDatabase.getTable("Table_3");
        if(res3.getCount() > 0) {
            //Check if this user reserve this table
            Cursor email1 = reserveDatabase.getEmail("Table_3");
            email1.moveToNext();
            if (auth.getCurrentUser().getEmail().equals(email1.getString(0)) ) {
                //Change button's name to modify if this user reserve it
                T3.setText(getString(R.string.text_modify));
            }
            else {
                //disable button if this user did not reserve this
                T3.setEnabled(false);
                T3.setText(getString(R.string.text_reserved));
            }
        }
        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "Table_3";
                id = 3;
                //Check if it is free to reserve
                if(T3.getText().equals(getString(R.string.text_reserve))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveDetailsActivity.class), 1);
                }
                //Go to modify if this user reserve once
                else if(T3.getText().equals(getString(R.string.text_modify))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveModifyActivity.class), 1);
                }

            }
        });
        // Check if someone reserve this table
        Cursor res4 = reserveDatabase.getTable("Table_4");
        if(res4.getCount() > 0) {
            //Check if this user reserve this table
            Cursor email1 = reserveDatabase.getEmail("Table_4");
            email1.moveToNext();
            if (auth.getCurrentUser().getEmail().equals(email1.getString(0)) ) {
                //Change button's name to modify if this user reserve it
                T4.setText(getString(R.string.text_modify));
            }
            else {
                //disable button if this user did not reserve this
                T4.setEnabled(false);
                T4.setText(getString(R.string.text_reserved));
            }
        }
        T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "Table_4";
                id = 4;
                //Check if it is free to reserve
                if(T4.getText().equals(getString(R.string.text_reserve))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveDetailsActivity.class), 1);
                }
                //Go to modify if this user reserve once
                else if(T4.getText().equals(getString(R.string.text_modify))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveModifyActivity.class), 1);
                }

            }
        });
        // Check if someone reserve this table
        Cursor res5 = reserveDatabase.getTable("Table_5");
        if(res5.getCount() > 0) {
            //Check if this user reserve this table
            Cursor email1 = reserveDatabase.getEmail("Table_5");
            email1.moveToNext();
            if (auth.getCurrentUser().getEmail().equals(email1.getString(0)) ) {
                //Change button's name to modify if this user reserve it
                T5.setText(getString(R.string.text_modify));
            }
            else {
                //disable button if this user did not reserve this
                T5.setEnabled(false);
                T5.setText(getString(R.string.text_reserved));
            }
        }
        T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "Table_5";
                id = 5;
                //Check if it is free to reserve
                if(T5.getText().equals(getString(R.string.text_reserve))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveDetailsActivity.class), 1);
                }
                //Go to modify if this user reserve once
                else if(T5.getText().equals(getString(R.string.text_modify))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveModifyActivity.class), 1);
                }

            }
        });
        // Check if someone reserve this table
        Cursor res6 = reserveDatabase.getTable("Table_6");
        if(res6.getCount() > 0) {
            //Check if this user reserve this table
            Cursor email1 = reserveDatabase.getEmail("Table_6");
            email1.moveToNext();
            if (auth.getCurrentUser().getEmail().equals(email1.getString(0)) ) {
                //Change button's name to modify if this user reserve it
                T6.setText(getString(R.string.text_modify));
            }
            else {
                //disable button if this user did not reserve this
                T6.setEnabled(false);
                T6.setText(getString(R.string.text_reserved));
            }
        }
        T6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "Table_6";
                id = 6;
                //Check if it is free to reserve
                if(T6.getText().equals(getString(R.string.text_reserve))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveDetailsActivity.class), 1);
                }
                //Go to modify if this user reserve once
                else if(T6.getText().equals(getString(R.string.text_modify))) {
                    startActivityForResult(new Intent(ReserveActivity.this, ReserveModifyActivity.class), 1);
                }
            }
        });
    }
    //send name
    public String getName() { return name; }//send id
    public int getId() { return id; }

    //Used after completing ReserveDetailsActivity or ReserveModifyActivity to refresh this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a table status has been updated, refresh this activity
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Intent refresh = new Intent(this, ReserveActivity.class);
                startActivity(refresh);
                finish();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
