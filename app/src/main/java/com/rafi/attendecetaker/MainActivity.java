package com.rafi.attendecetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.rafi.attendecetaker.Database.Constant;
import com.rafi.attendecetaker.Database.DbHelper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    DbHelper dbHelper;

    int  selectUser;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        email = getIntent().getStringExtra("email");

        floatingActionButton = findViewById(R.id.floatingaction);
        floatingActionButton.setOnClickListener(v -> showDialog());

        loadData();

        recyclerView = findViewById(R.id.recyclerviewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classAdapter = new ClassAdapter(classItems, this);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(postion -> getItemActivity(postion));

        // Toolbar is call here.
        setToolbar();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable(email);
        classItems.clear();
        //Toast.makeText(getApplicationContext(),"Rafi"+email,Toast.LENGTH_LONG).show();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String className = cursor.getString(1);
            String subjectName = cursor.getString(2);

            classItems.add(new ClassItem(id, className, subjectName));
        }
    }

    // This method is for the set the toolbar .
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarMainId);
        TextView title = toolbar.findViewById(R.id.title_Main_toolbar);
        drawerLayout = findViewById(R.id.mainDrawerLayoutID);

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
        selectUser = Integer.parseInt(getIntent().getStringExtra("admin"));
        NavigationView navigationView = (NavigationView) findViewById(R.id.mainActivity_NavigationViewId);
        View headerView = navigationView.getHeaderView(0);
        TextView headerEmail = headerView.findViewById(R.id.teacher_nav_header_email_textfield);
        TextView headerName = headerView.findViewById(R.id.teacher_nav_header_name_textfield);
        CircleImageView headerProfile = headerView.findViewById(R.id.teacher_nav_profile_Image);



            Cursor cursor = new DbHelper(this).registrationEmail(email);
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
                        Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        break;
                    case R.id.teacher_menu_item_AboutUs:
                        Intent intent5 = new Intent(MainActivity.this, ShowFeedback.class);
                        startActivity(intent5);
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
            Intent intent = new Intent(MainActivity.this, TeacherProfile.class);
            intent.putExtra("admin","1");
            intent.putExtra("email", email);
            startActivity(intent);
        });

    }

    private void getItemActivity(int postion) {
        Intent intent = new Intent(MainActivity.this, StudentActivity.class);
        intent.putExtra("className", classItems.get(postion).getClassname());
        intent.putExtra("subjectName", classItems.get(postion).getSubjectname());
        intent.putExtra("Cid", classItems.get(postion).getC_id());
        intent.putExtra("position", postion);
        startActivity(intent);
    }

    // This method is for the show the alert dialog .
    private void showDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((classname, subjectname) -> addClass(classname, subjectname));
    }

    // Add the class to the database.
    private void addClass(String className, String subjectName) {
        long cid = dbHelper.addClass(className, subjectName, email);
        ClassItem classItem = new ClassItem(className, subjectName);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();
    }

    // To Delete Or Update the class item
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
                break;
        }


        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog(classItems.get(position).getClassname(), classItems.get(position).getSubjectname());
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className, subjectName) -> updateClass(position, className, subjectName));
    }

    private void updateClass(int position, String className, String subjectName) {
        dbHelper.updateClass(classItems.get(position).getC_id(), className, subjectName);
        // This two line is for the update the class name in the RecyclerView User Interface.
        classItems.get(position).setClassname(className);
        classItems.get(position).setSubjectname(subjectName);
        classAdapter.notifyItemChanged(position);


    }

    private void deleteClass(int position) {
        // Specefiq position data will be removed from the database.
        dbHelper.deleteClass(classItems.get(position).getC_id());
        // This two line is used for the remove tha class item from recyclerView User Interface.
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}
