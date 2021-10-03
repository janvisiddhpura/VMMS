package com.mad.vmms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    DetailAdapter adapter;
    ArrayList<Entry> entryList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "user";
    String valUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        this.setTitle("Refueling History");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tool)));

        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        valUid = sharedPreferences.getString("id", "");

        recyclerView = findViewById(R.id.rvData);

        entryList = getEntries();

        adapter = new DetailAdapter(entryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(ShowDetails.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    public ArrayList<Entry> getEntries() {
        ArrayList<Entry> entryList = new ArrayList<>();
        Cursor cursor = databaseHelper.selectEntries(valUid);

        if (cursor.moveToFirst()) {
            do {
                Entry entry = new Entry(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)),
                        Float.parseFloat(cursor.getString(4)),
                        cursor.getString(5),
                        cursor.getString(6));
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return entryList;
    }

}