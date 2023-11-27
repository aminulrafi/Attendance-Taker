package com.rafi.attendecetaker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.rafi.attendecetaker.Database.DbHelper;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherRegistration extends AppCompatActivity {
    TextInputEditText nameEdittext, emailEdittext, numberEditext, passwordEdittext;
    Button teacherAddclickbtn;
    LinearLayout linearLayoutAdmin;
    View adminView;
    TextView teacherBackbtn;
    CircleImageView teacherImageView;

    // This two variable is used for the image.
    int SELECT_IMAGE_CODE =1;
    private Bitmap imageTostore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        nameEdittext = findViewById(R.id.TeachernamefieldId);
        numberEditext = findViewById(R.id.TeachernumberfieldId);
        emailEdittext = findViewById(R.id.TeacheremailfieldId);
        passwordEdittext = findViewById(R.id.TeacherpasswordfieldId);
        teacherAddclickbtn = findViewById(R.id.TeacherbuttonId);
        teacherBackbtn = findViewById(R.id.TeacherTextViewId);
        teacherImageView = findViewById(R.id.Teacherprofile_image);
        linearLayoutAdmin = findViewById(R.id.adminSignUpId);
        adminView = findViewById(R.id.adminSignUpView);

        setToolbar();

        adminView.setVisibility(View.GONE);
        linearLayoutAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherRegistration.this,AdminRegistration.class);
            startActivity(intent);
            finish();
        });
        teacherBackbtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherRegistration.this,LoginAcrivity.class);
            startActivity(intent);
            finish();
        });
        // Intialize DbHelper
        DbHelper dbHelper = new DbHelper(this);
        //This on click listener is used for the take the input form the gallery.
        teacherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), SELECT_IMAGE_CODE);
            }
        });

        // On Click Listener for the sing in button.
        teacherAddclickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdittext.getText().toString();
                String number = numberEditext.getText().toString();
                String email = emailEdittext.getText().toString();
                String password = passwordEdittext.getText().toString();

                if(imageTostore == null | TextUtils.isEmpty(name) | TextUtils.isEmpty(number) | TextUtils.isEmpty(email) | TextUtils.isEmpty(password)){

                    if (imageTostore == null){Toast.makeText(getApplicationContext(),"Enter the picture",Toast.LENGTH_LONG).show();return;}
                    if (TextUtils.isEmpty(name)){nameEdittext.setError("Ener the name ");return;}
                    if (TextUtils.isEmpty(number)){numberEditext.setError("Enter the password");return;}
                    if (TextUtils.isEmpty(email)){emailEdittext.setError("Enter the email ");return;}
                    if (TextUtils.isEmpty(password)){passwordEdittext.setText("Enter the password ");return;}
                }else {
                    dbHelper.addTeacherRegistrationTable(new TeacherItems(name,number,email,password,imageTostore));
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


        title.setText("Registration Teacher");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        back.setOnClickListener(V -> onBackPressed());
    }

    // This methdo is used for the set the image to the circular image view.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                Uri uri = data.getData();
                imageTostore = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                teacherImageView.setImageURI(uri);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}