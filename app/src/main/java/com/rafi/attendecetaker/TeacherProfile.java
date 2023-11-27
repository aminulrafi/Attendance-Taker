package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.DbHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfile extends AppCompatActivity {
    TextView showName, showEmail, showNumber;
    CircleImageView setImage;
    Toolbar toolbar;
    Button editButton;
    String email;
    int selectUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        email = getIntent().getStringExtra("email");
        selectUser = Integer.parseInt(getIntent().getStringExtra("admin"));
        showName = findViewById(R.id.showProfileNamefieldId);
        showEmail = findViewById(R.id.showProfileEmailFieldId);
        showNumber = findViewById(R.id.showProfileNumberFieldId);
        setImage = findViewById(R.id.showProfileImageId);
        editButton = findViewById(R.id.editButton);

        setToolbar();
        if (selectUser == 1) {
            Cursor cursor = new DbHelper(getApplicationContext()).registrationEmail(email);
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String number = String.valueOf(cursor.getInt(1));
            String email = cursor.getString(2);
            byte[] img = cursor.getBlob(4);
            Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
            showName.setText(name);
            showEmail.setText(email);
            showNumber.setText("0" + number);
            setImage.setImageBitmap(image);
        } else {
            Cursor cursor = new DbHelper(getApplicationContext()).registrationEmailAdmin(email);
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String number = String.valueOf(cursor.getInt(1));
            String email = cursor.getString(2);
            byte[] img = cursor.getBlob(4);
            Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
            showName.setText(name);
            showEmail.setText(email);
            showNumber.setText("0" + number);
            setImage.setImageBitmap(image);
        }
        if (selectUser == 1) {
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherProfile.this, TeacherUpdateProfile.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            });
        } else {
            editButton.setVisibility(View.GONE);
        }
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarId);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageView back, save;
        back = toolbar.findViewById(R.id.backIconId);
        save = toolbar.findViewById(R.id.save);

        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        title.setText("Profile");

        back.setOnClickListener(V -> onBackPressed());
    }
}