package com.mad.vmms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Refuel_Entry extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    Cursor cursor;
    EditText edtVolume, edtPrice, edtOdometer;
    TextView txtDate;
    Button btnRefuel;
    boolean vVolume, vPrice, vOdometer;
    String prefName = "user", valUid = "", valVolume = "", valPrice = "", valOdometer = "", price, odometer, did;
    float valAvg = 0, valRs = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    float avg,rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel_entry);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        this.setTitle("ADD REFUEL DETAILS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tool)));

        valUid = sharedPreferences.getString("id", "");

        txtDate = findViewById(R.id.edtDate);
        txtDate.setText("   "+currentDate());

        edtVolume = findViewById(R.id.edtVolume);
        edtPrice = findViewById(R.id.edtPrice);
        edtOdometer = findViewById(R.id.edtOdometer);
        btnRefuel = findViewById(R.id.btnRefuel);
        btnRefuel.setOnClickListener(v -> {
            validateDetails();
        });

    }

    private void validateDetails() {
        valVolume = edtVolume.getText().toString();
        valPrice = edtPrice.getText().toString();
        valOdometer = edtOdometer.getText().toString();

        if (valVolume.isEmpty() && valPrice.isEmpty() && valOdometer.isEmpty()) {
            AlertMessage(Refuel_Entry.this, "All Fields are Required.!", "Alert !!");
        } else {
            if (valVolume.isEmpty())
                edtVolume.setError("This field is required.!");
            else
                vVolume = true;

            if (valPrice.isEmpty())
                edtPrice.setError("This field is required.!");
            else
                vPrice = true;

            if (valOdometer.isEmpty())
                edtOdometer.setError("This field is required.!");
            else
                vOdometer = true;

        }
        if (vVolume && vPrice && vOdometer) {
            doAddDetails();
        }
    }

    private void doAddDetails() {

        int total = ((Integer.parseInt(valVolume)) * Integer.parseInt(valPrice));

        cursor = databaseHelper.selectEntry(valUid);

        if (cursor != null && cursor.getCount()==1) {
            cursor.moveToLast();
            did = cursor.getString(0);
            price = cursor.getString(1);
            odometer = cursor.getString(2);

            avg = ((Float.parseFloat(valOdometer) - Float.parseFloat((odometer))) / Float.parseFloat(valVolume));
            rs = (Float.parseFloat(price) / avg);

//            Toast.makeText(getApplicationContext(), "did "+did+" price "+price+" odometer" +odometer+" avg", Toast.LENGTH_SHORT).show();

        }

        if (databaseHelper.insertFuel(valUid, currentDate(), valVolume, valPrice, String.valueOf(total), valOdometer, String.valueOf(valAvg), String.valueOf(valRs))) {

            if (databaseHelper.updateEntry(valUid, did, String.format("%.2f", avg), String.format("%.2f", rs)))
            {
                Toast.makeText(getApplicationContext(), "Data Added Successfully.!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Data Added Successfully.!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong ! Please try again .!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, Dashboard.class);
//            startActivity(intent);
//            finish();
        }
    }

    private String currentDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String curDate = mDay + "/" + mMonth + "/" + mYear;
        return curDate;
    }

    private void AlertMessage(Context context, String str, String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.ic_twotone_info);
        alert.setTitle(title);
        alert.setMessage(str);
        alert.setPositiveButton("OK", (dialogInterface, i) -> {
            edtVolume.setFocusable(true);
        });
        alert.show();
    }
}
