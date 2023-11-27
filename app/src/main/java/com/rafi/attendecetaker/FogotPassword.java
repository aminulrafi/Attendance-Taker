package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.DbHelper;

public class FogotPassword extends AppCompatActivity {
    EditText emailSearch, password, retypePassword;
    Button buttonSearch, buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);

        // This method set the toolbar.
        setToolbar();

        emailSearch = findViewById(R.id.resetEmail);
        password = findViewById(R.id.resetPassword);
        retypePassword = findViewById(R.id.resetRetypePassword);
        buttonSearch = findViewById(R.id.resetSearchButton);
        buttonUpdate = findViewById(R.id.resetButton);


        password.setVisibility(View.GONE);
        retypePassword.setVisibility(View.GONE);
        buttonUpdate.setVisibility(View.GONE);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailSearch.getText().toString();
                Cursor cursor = new DbHelper(getApplicationContext()).registrationEmail(email);
                if (cursor.getCount() > 0) {
                    password.setVisibility(View.VISIBLE);
                    retypePassword.setVisibility(View.VISIBLE);
                    buttonUpdate.setVisibility(View.VISIBLE);
                } else {
                    password.setVisibility(View.GONE);
                    retypePassword.setVisibility(View.GONE);
                    buttonUpdate.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "No Account Against This Email", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordd = password.getText().toString();
                String retypepassword = retypePassword.getText().toString();
                String email = emailSearch.getText().toString();

                if (passwordd.equals(retypepassword)) {
                    long value = new DbHelper(getApplicationContext()).forgotPasswordTeacher(passwordd, email);
                    if (value != -1) {
                        Intent intent = new Intent(FogotPassword.this,LoginAcrivity.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Retype Password Mismatch", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarId);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.backIconId);
        ImageButton save = toolbar.findViewById(R.id.save);

        // Listener for the save button.


        title.setText("Forgot Password");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        back.setOnClickListener(V -> onBackPressed());
    }
}