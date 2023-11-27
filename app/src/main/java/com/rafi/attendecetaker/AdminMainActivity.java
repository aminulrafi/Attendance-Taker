package com.rafi.attendecetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.rafi.attendecetaker.Database.DbHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    String selectUser;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_activity);

        getSupportFragmentManager().beginTransaction().replace(R.id.admin_frameLayout, new AdminAllTeacherFragment()).commit();

        // Toolbar is call here.
        setToolbar();
    }

    // This method is for the set the toolbar .
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarMainId);
        TextView title = toolbar.findViewById(R.id.title_Main_toolbar);
        drawerLayout = findViewById(R.id.adminMainDrayerLayout);

        // add the toolbar.
        setSupportActionBar(toolbar);
        // Toogle is java file that show the three dot line .
        //Direct parameter string is not given in the ActionBarDrawerToggle .Thats why decleared in the string file.
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        //Toggle is listen by the drawerlayout.
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /* This piece of code is used to find out  the navigation header item. */
        email = getIntent().getStringExtra("email");
        selectUser = getIntent().getStringExtra("admin");
        NavigationView navigationView = (NavigationView) findViewById(R.id.mainActivity_NavigationViewId);
        View headerView = navigationView.getHeaderView(0);
        TextView headerEmail = headerView.findViewById(R.id.teacher_nav_header_email_textfield);
        TextView headerName = headerView.findViewById(R.id.teacher_nav_header_name_textfield);
        CircleImageView headerProfile = headerView.findViewById(R.id.teacher_nav_profile_Image);

        Cursor cursor = new DbHelper(this).registrationEmailAdmin(email);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String name = cursor.getString(0);
            byte[] img = cursor.getBlob(4);
            Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
            headerEmail.setText(email);
            headerName.setVisibility(View.GONE);
            headerProfile.setImageBitmap(image);
        }
        /* Navigation Header Work Finished Here */
        title.setText("Attendence App");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.teacher_menu_item_Home:
                        Toast.makeText(getApplicationContext(), "Home Panel Open", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.teacher_menu_item_Comment:
                        Toast.makeText(getApplicationContext(), "Comment Panel Open", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.teacher_menu_item_AboutUs:
                        Toast.makeText(getApplicationContext(), "About Panel Open", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.teacher_menu_item_Logout:
                        Toast.makeText(getApplicationContext(), "Logout Panel Open", Toast.LENGTH_LONG).show();
                        break;
                }

                //This is used to close the drawer when if click on inside drawer.Normally it is close when
                // we click outside the drawer weight.
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // On Click Listener On the header profile Image.
        headerProfile.setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, TeacherProfile.class);
            intent.putExtra("admin","2");
            intent.putExtra("email", email);
            startActivity(intent);
        });

    }
}