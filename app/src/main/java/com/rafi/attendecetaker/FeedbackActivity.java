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

import com.google.android.material.textfield.TextInputEditText;
import com.rafi.attendecetaker.Database.DbHelper;

public class FeedbackActivity extends AppCompatActivity {
    EditText occupationEditText;
    TextInputEditText textBox;
    TextView nameTextView;
    Button addFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        nameTextView = findViewById(R.id.feedbackNameEdittext);
        occupationEditText = findViewById(R.id.feedbackOccupationEdittext);
        textBox = findViewById(R.id.feedBackTextInputfieldId);
        addFeedBack = findViewById(R.id.feedbackButtonID);

        nameTextView.setText(getIntent().getStringExtra("email"));
        Cursor cursor = new DbHelper(getApplicationContext()).checkUserAlreadyExists(getIntent().getStringExtra("email"));
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            occupationEditText.setText(cursor.getString(1));

        // This function is used for the toolbar .
        setToolbar();


        addFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = nameTextView.getText().toString();
                String occupation = occupationEditText.getText().toString();
                String feedback = textBox.getText().toString();
                if (cursor.getCount() > 0) {
                    new DbHelper(getApplicationContext()).updateFeedbackTable(feedback, email);
                } else
                    new DbHelper(getApplicationContext()).addFeedback(new FeedbackItems(email, occupation, feedback, "1"));
                Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                intent.putExtra("admin", "1");
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
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


        title.setText("Feedback Page");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        back.setOnClickListener(V -> onBackPressed());
    }
}