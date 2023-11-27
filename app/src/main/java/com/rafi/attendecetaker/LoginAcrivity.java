package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.DbHelper;

public class LoginAcrivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button addTodatabase;
    TextView SignUpPage, forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acrivity);

        DbHelper dbHelper = new DbHelper(this);
        // This method set the toolbar
        setToolbar();
        emailEditText = findViewById(R.id.resetEmail);
        passwordEditText = findViewById(R.id.resetRetypePassword);
        addTodatabase = findViewById(R.id.resetButton);
        SignUpPage = findViewById(R.id.adminLoginSignUpId);
        forgotPassword = findViewById(R.id.forgotPassword);

        //  Sign In Page Login
        addTodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email) | TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(email)) {
                        emailEditText.setError("Enter email ");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        passwordEditText.setError("Enter Password ");
                        return;
                    }
                } else {
                    Cursor cursor = new DbHelper(getApplicationContext()).registrationLogin(email, password);
                    Cursor cursor2 = new DbHelper(getApplicationContext()).registrationAdminLogin(email, password);
                    Cursor cursor1 = new DbHelper(getApplicationContext()).registrationEmail(email);
                    if (cursor.getCount() > 0 && cursor2.getCount() > 0) {
                        Intent intent = new Intent(LoginAcrivity.this, LoginModeSelection.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else if (cursor.getCount() > 0) {
                        Intent intent = new Intent(LoginAcrivity.this, MainActivity.class);
                        intent.putExtra("admin","2");
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else if (cursor1.getCount() > 0) {
                        Toast.makeText(getApplicationContext(), "Create an account", Toast.LENGTH_LONG).show();
                    } else if (cursor2.getCount() > 0) {
                        Intent intent = new Intent(LoginAcrivity.this,AdminMainActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("admin","1");
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Valid Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAcrivity.this, FogotPassword.class);
                startActivity(intent);
            }
        });

        SignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAcrivity.this, TeacherRegistration.class);
                startActivity(intent);
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


        title.setText("Login");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        back.setOnClickListener(V -> onBackPressed());
    }
}