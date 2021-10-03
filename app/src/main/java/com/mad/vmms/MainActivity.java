package com.mad.vmms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    Cursor cursor;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "user";
    EditText sEmail, sPassword;
    Button btnsLogin;
    TextView txtSignup;
    String id, name, valUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        valUser = sharedPreferences.getString("username","");
        if (!valUser.equals(""))
        {
            Intent intent = new Intent(getApplicationContext(),Dashboard.class);
            startActivity(intent);
            finish();
        }
        else
        {
            sEmail = findViewById(R.id.sEmail);
            sPassword = findViewById(R.id.sPassword);
            txtSignup = findViewById(R.id.txtSignUp);
            btnsLogin = findViewById(R.id.btnsLogin);

            txtSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Register.class);
                    startActivity(intent);
                }
            });

            btnsLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String valUsername = sEmail.getText().toString();
                    String valPassword = sPassword.getText().toString();

                    if (!valUsername.isEmpty() && !valPassword.isEmpty()) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(valUsername).matches()) {
                            Toast.makeText(getApplicationContext(), "Email address format is not proper.", Toast.LENGTH_SHORT).show();
                        } else {
                            cursor = databaseHelper.selectUser(valUsername, valPassword);
                            if (cursor != null && cursor.getCount() == 1) {
                                cursor.moveToFirst();
                                do {
                                    id = cursor.getString(0);
                                    name = cursor.getString(1);
                                }while (cursor.moveToNext());
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                editor.putString("username", name);
                                editor.putString("id",id);
                                editor.commit();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "You're not Registered..! Please do Signup.! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        if (valUsername.isEmpty()) {
                            sEmail.setError("This field is required.");
                        }
                        if (valPassword.isEmpty()) {
                            sPassword.setError("This field is required.");
                        }
                    }
                }
            });
        }
    }
}