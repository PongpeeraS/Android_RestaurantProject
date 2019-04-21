package com.example.restaurantproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.restaurantproject.Reserve.ReserveDatabase;

import java.util.Calendar;

//This class trigger after user reserve and want to modify
public class ReserveModifyActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String tag = "Detail";
    private TextView datem, timem, pass;
    private Button update, abandon;
    private DatePickerDialog.OnDateSetListener d;
    ReserveDatabase reserveDatabase;
    private ReserveActivity test = new ReserveActivity();
    private ReserveDetailsActivity detail = new ReserveDetailsActivity();
    //Set text of time picker
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(minute < 10) {
            timem.setText(getString(R.string.text_time) + hourOfDay + ":0" + minute);
        }
        else {
            timem.setText(getString(R.string.text_time) + hourOfDay + ":" + minute);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_modify);
        setTitle(R.string.title_reserve_modify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datem = findViewById(R.id.datem);
        timem = findViewById(R.id.timem);
        update = findViewById(R.id.Update);
        abandon = findViewById(R.id.Abandon);
        reserveDatabase = new ReserveDatabase(this);
        pass = findViewById(R.id.password);
        //Set date to be the same as database
        Cursor resdate = reserveDatabase.getDate(test.getName());
        resdate.moveToNext();
        datem.setText(resdate.getString(0));
        //Set time to be the same as database
        Cursor restime = reserveDatabase.getTime(test.getName());
        restime.moveToNext();
        timem.setText(restime.getString(0));

        //trigger time picker
        final TextView timem = findViewById(R.id.timem);
        timem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(),"Time picker");

            }
        });
        //Update button for new date and time for new value
        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor email1 = reserveDatabase.getEmail(test.getName());
                email1.moveToNext();
                boolean isupdate = reserveDatabase.updateData(test.getName(), (String) datem.getText(), (String)timem.getText(), (email1.getString(0)),detail.getPass());
                Toast.makeText(ReserveModifyActivity.this, getString(R.string.text_updated), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, null);
                finish();
            }
        });
        //the date test for select date
        datem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //Set theme
                DatePickerDialog dialog = new DatePickerDialog(
                        ReserveModifyActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        d,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        //Set date formation
        d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
            {
                Log.d(tag, "onDataset: date: " + year + "/" + month + "/" + dayOfMonth);
                month = month + 1;
                String da = month + "/" + dayOfMonth + "/" + year;
                datem.setText(da);
            }
        };
        //Abandon button for cancel the reserve
        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(ReserveModifyActivity.this);
                builder.setMessage(getString(R.string.text_cancel_confirm) +"(" +test.getName()+")");
                builder.setPositiveButton(getString(R.string.text_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Integer delete = reserveDatabase.delete(test.getName());
                        setResult(RESULT_OK, null);
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
        //Get password to show to user (use when go to the restaurant)
        Cursor password = reserveDatabase.getPass(test.getName());
        password.moveToNext();
        pass.setText(getString(R.string.text_reserve_password) + password.getString(0));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
