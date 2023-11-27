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

public class AdminRegistration extends AppCompatActivity {
    TextInputEditText nameEdittext, emailEdittext, numberEditext, passwordEdittext;
    Button adminAddclickbtn;
    LinearLayout linearLayoutUser;
    TextView backToLogin;
    CircleImageView adminImageView;
    View userView;

    // This two variable is used for the image.
    int SELECT_IMAGE_CODE =1;
    private Bitmap imageTostore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        nameEdittext = findViewById(R.id.AdminnamefieldId);
        numberEditext = findViewById(R.id.AdminnumberfieldId);
        emailEdittext = findViewById(R.id.AdminemailfieldId);
        passwordEdittext = findViewById(R.id.AdminpasswordfieldId);
        adminAddclickbtn = findViewById(R.id.AdminbuttonId);
        adminImageView = findViewById(R.id.Adminprofile_image);
        linearLayoutUser = findViewById(R.id.userSignUpId1);
        backToLogin = findViewById(R.id.AdminTextViewId);
        userView = findViewById(R.id.userSignUpVieww);

        setToolbar();

        userView.setVisibility(View.GONE);
        linearLayoutUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminRegistration.this,TeacherRegistration.class);
            startActivity(intent);
            finish();
        });

        // Intialize DbHelper
        DbHelper dbHelper = new DbHelper(this);
        //This on click listener is used for the take the input form the gallery.
        adminImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), SELECT_IMAGE_CODE);
            }
        });
        //To Add Admin Infromation to the database.
        adminAddclickbtn.setOnClickListener(new View.OnClickListener() {
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
                    dbHelper.addAdminRegistrationTable(new TeacherItems(name,number,email,password,imageTostore));
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRegistration.this,LoginAcrivity.class);
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


        title.setText("Registration Admin");
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
                adminImageView.setImageURI(uri);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}