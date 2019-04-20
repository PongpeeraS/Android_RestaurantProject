package com.example.restaurantproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantproject.Reserve.TimePickerFragment;
import com.example.restaurantproject.Reserve.reserveDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Random;

//This class is the further class when user reserve for the first time
public class ReserveDetailsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String tag = "Detail";
    private TextView date, time;
    private Button confirm, cancel;
    private DatePickerDialog.OnDateSetListener d;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public static ReserveActivity test = new ReserveActivity();
    public static boolean[] id = {false,false,false,false,false,false};
    reserveDatabase reserveDatabase;
    public static String pass;

    //Set text for time picker
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(minute < 10) {
            time.setText(getString(R.string.text_time) + hourOfDay + ":0" + minute);
        }
        else {
            time.setText(getString(R.string.text_time) + hourOfDay + ":" + minute);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_detail);
        date = findViewById(R.id.Date);
        confirm = findViewById(R.id.Confirm);
        cancel = findViewById(R.id.Cancel);
        reserveDatabase = new reserveDatabase(this);
        time = findViewById(R.id.Time);

        //Click date text for select date
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReserveDetailsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        d,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        //Set formation for date picker
        d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
            {
                Log.d(tag, "onDataset: date: " + year + "/" + month + "/" + dayOfMonth);
                month = month + 1;
                String da = month + "/" + dayOfMonth + "/" + year;
                date.setText(da);
            }
        };
        //Set on time text
        final TextView time = (TextView) findViewById(R.id.Time);
        time.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(),"Time picker");
            }
        });
        //Confirm button for reserve
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                id[test.getId()-1] = true;
                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(ReserveDetailsActivity.this);
                //When user click confirm, it will trigger message Yes or No
                builder.setMessage(getString(R.string.text_reserve_confirm) +"(" +test.getName()+")");
                builder.setPositiveButton(getString(R.string.text_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Click yes for confirm, add table to database and generate password
                        pass = getRandomNumberString();
                        AddTable(test.getName(),date.getText().toString(),
                                time.getText().toString(), auth.getCurrentUser().getEmail(), pass);
                        setResult(RESULT_OK, null);
                        finish();
                        //TODO remove cancel(BACK) button to back button?
                    }
                });
                //Click no, do nothing
                builder.setNegativeButton(getString(R.string.text_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        //Cancel button for go back
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean[] getId()
    {
        return id;
    }
    //Add table to database method
    public void AddTable(String name,String date, String time, String email, String pass) {
        boolean insert = reserveDatabase.addTable(name,date,time,email,pass);
        if (insert) {
            Toast.makeText(ReserveDetailsActivity.this, getString(R.string.text_reserved), Toast.LENGTH_LONG).show();
        }
    }
    //Random method for password
    @SuppressLint("DefaultLocale")
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public String getPass()
    {
        return pass;
    }

}

