package com.rafi.attendecetaker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.DbHelper;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherUpdateProfile extends AppCompatActivity {
    EditText showName, showEmail, showNumber, password, retyPassword;
    CircleImageView setImage;
    Toolbar toolbar;
    Button editButton;
    String email;
    int SELECT_IMAGE_CODE = 1;
    private Bitmap imageTostore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_profile);

        email = getIntent().getStringExtra("email");
        editButton = findViewById(R.id.updateButton);
        setImage = findViewById(R.id.updateProfileImageId);
        showName = findViewById(R.id.updateProfileNamefieldId);
        showEmail = findViewById(R.id.updateProfileEmailFieldId);
        showNumber = findViewById(R.id.updateProfileNumberFieldId);
        password = findViewById(R.id.updateProfilePasswordFieldId);
        retyPassword = findViewById(R.id.updateProfileReTypePasswordFieldId);


        setToolbar();

        Cursor cursor = new DbHelper(getApplicationContext()).registrationEmail(email);
        cursor.moveToFirst();
        String name = cursor.getString(0);
        String number = String.valueOf(cursor.getInt(1));
        String emaill = cursor.getString(2);
        String Password = cursor.getString(3);
        byte[] img = cursor.getBlob(4);
        imageTostore = BitmapFactory.decodeByteArray(img, 0, img.length);
        showName.setText(name);
        showEmail.setText(emaill);
        showNumber.setText("0" + number);
        password.setText(Password);
        retyPassword.setText(Password);
        setImage.setImageBitmap(imageTostore);

        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), SELECT_IMAGE_CODE);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = showName.getText().toString();
                String number1 = showNumber.getText().toString();
                String email1 = showEmail.getText().toString();
                String Password1 = password.getText().toString();
                String reTypePassword = retyPassword.getText().toString();
                if (Password1.equals(reTypePassword)) {
                    long num = new DbHelper(getApplicationContext()).uniqueEmailPasswordTeacher(email1);
                    if (num > 0) {
                        Toast.makeText(getApplicationContext(), "This User Already Exists", Toast.LENGTH_LONG).show();
                    } else {
                        new DbHelper(getApplicationContext()).updateTeacherInformation(new TeacherItems(name1, number1, email1, Password1, imageTostore), email);
                        Intent intent = new Intent(TeacherUpdateProfile.this,LoginAcrivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "ReTpe Password Mismatch", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    //This method is used for storing the image in the circular imageView.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                Uri uri = data.getData();
                imageTostore = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                setImage.setImageURI(uri);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}