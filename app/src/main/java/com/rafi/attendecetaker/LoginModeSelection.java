package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class LoginModeSelection extends AppCompatActivity {
    LinearLayout userLinearLayout,adminLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mode_selection);

        userLinearLayout = findViewById(R.id.userNextActivity);
        adminLinearLayout = findViewById(R.id.adminNextActivity);

        userLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(LoginModeSelection.this,MainActivity.class);
            intent.putExtra("admin","1");
            intent.putExtra("email",getIntent().getStringExtra("email"));
            startActivity(intent);
        });
        adminLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginModeSelection.this,AdminMainActivity.class);
                intent.putExtra("admin","1");
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });
    }
}