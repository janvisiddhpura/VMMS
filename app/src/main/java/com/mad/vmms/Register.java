package com.mad.vmms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword, edtContact, edtLicence;
    String userName = "", userEmail = "", userPassword = "", userConfirmPwd = "", userContact = "", userLicence = "";
    boolean vName, vEmail, vPassword, vConfirmpwd, vContact, vLicence;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        edtContact = (EditText) findViewById(R.id.edtContact);
        edtLicence = (EditText) findViewById(R.id.edtLicence);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDetails();
            }
        });

    }

    private void validateDetails() {
        userName = edtUsername.getText().toString();
        userEmail = edtEmail.getText().toString();
        userPassword = edtPassword.getText().toString();
        userConfirmPwd = edtConfirmPassword.getText().toString();
        userContact = edtContact.getText().toString();
        userLicence = edtLicence.getText().toString();
        if (userName.isEmpty() && userEmail.isEmpty() && userPassword.isEmpty() && userConfirmPwd.isEmpty() && userContact.isEmpty() && userLicence.isEmpty())
        {
            AlertMessage(Register.this,"All Fields are Required.!","Alert !!");
        }
        else
        {
            if (userName.isEmpty())
                edtUsername.setError("Username id required.!");
            else
                vName = true;

            if (userEmail.isEmpty())
                edtEmail.setError("Email is required.");
            else
                if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
                    edtEmail.setError("Username must be proper email address.!");
                else
                    vEmail = true;

            if (userPassword.isEmpty())
                edtPassword.setError("Password is required.");
            else
                if(userPassword.length()<7)
                    edtPassword.setError("Password Length must be greater than 6.!");
                else
                    vPassword = true;

            if(userConfirmPwd.isEmpty())
            {
                edtConfirmPassword.setFocusable(true);
                edtConfirmPassword.setError("Confirm Password is required.");
            }
            else
            {
                if (!userConfirmPwd.equals(userPassword))
                {
                    edtConfirmPassword.setFocusable(true);
                    edtConfirmPassword.setError("Must be same as Password.");
                }
                else
                    vConfirmpwd = true;
            }

            if(userContact.isEmpty())
            {
                edtContact.setFocusable(true);
                edtContact.setError("Contact Number is required.");
            }
            else
            {
                if (!userContact.matches("^[0-9]{0,10}$"))
                {
                    edtContact.setFocusable(true);
                    edtContact.setError("Enter valid mobile number.");
                }
                else
                    vContact = true;
            }

            if(userLicence.isEmpty())
            {
                edtLicence.setFocusable(true);
                edtLicence.setError("This Field is required.");
            }
            else
            {
                if (!userLicence.matches("^[0-9]{0,12}$"))
                {
                    edtLicence.setFocusable(true);
                    edtLicence.setError("Enter valid Licence number.");
                }
                else
                    vLicence = true;
            }
        }
        if (vName && vEmail && vPassword && vConfirmpwd && vContact && vLicence)
        {
            doRegister();
        }
    }

    private void doRegister() {

        if (databaseHelper.insert(userName,userEmail,userPassword,userContact,userLicence))
        {
            Toast.makeText(getApplicationContext(), "Registered Successfully.! Now you can Login.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Something went wrong ! Please try again .!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
            finish();
        }
    }

    private void AlertMessage(Context context, String str, String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.ic_twotone_info);
        alert.setTitle(title);
        alert.setMessage(str);
        alert.setPositiveButton("OK", (dialogInterface, i) -> {
            edtUsername.setFocusable(true);
        });
        alert.show();
    }

}

