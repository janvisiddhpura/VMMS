package com.mad.vmms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    CardView cdAddDetail, cdShowDetail;
    TextView txtUsername;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "user";
    String val = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        this.setTitle("DASHBOARD");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tool)));

        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        val = sharedPreferences.getString("username","");

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText("WELCOME "+val);

        cdAddDetail = (CardView) findViewById(R.id.cdAddDetail);
        cdShowDetail = (CardView) findViewById(R.id.cdShowDetail);

        cdAddDetail.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Refuel_Entry.class);
            startActivity(intent);
        });

        cdShowDetail.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),ShowDetails.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            AlertMessage(Dashboard.this,"Are you sure for logout?");
        }
        return super.onOptionsItemSelected(item);
    }

    private void AlertMessage(Context context, String str) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.ic_twotone_info);
        alert.setTitle("Confirm !!!");
        alert.setMessage(str);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("Not Now",null);
        alert.show();
    }

}