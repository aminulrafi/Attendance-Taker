package com.rafi.attendecetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
//import androidx.appcompat.widget.Toolbar;
import com.rafi.attendecetaker.Database.Constant;
import com.rafi.attendecetaker.Database.DbHelper;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class StudentActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String className, subjectName;
    private int positon;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems = new ArrayList<>();
    private DbHelper dbHelper;
    private long cid;
    MyCalendar calendar;
    TextView subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_activity);
        // Call the toolbar here.

        calendar = new MyCalendar();
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        className = intent.getStringExtra("className");
        subjectName = intent.getStringExtra("subjectName");
        positon = intent.getIntExtra("position", -1);
        cid = intent.getLongExtra("Cid", -1);
        studentItems.clear();

        // This function set the toolbar .
        setToolbar();

        // This method is used for the load the data into the studentItems Arraylist.
        loadData();

        recyclerView = findViewById(R.id.student_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentItems, this);
        recyclerView.setAdapter(adapter);

        // This method is for the update the status of the student.
        adapter.setOnItemClickListener(positon -> changeStatus(positon));
        loadStatusData();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getStudentTable(cid);
        while (cursor.moveToNext()) {
            long sid = cursor.getInt(0);
            String name = cursor.getString(2);
            int roll = cursor.getInt(3);
            studentItems.add(new StudentItem(sid, roll, name));
        }
        cursor.close();
    }

    private void changeStatus(int positon) {
        String status = studentItems.get(positon).getStatus();

        if (status.equals("P")) {
            status = "A";
        } else {
            status = "P";
        }
        studentItems.get(positon).setStatus(status);
        adapter.notifyItemChanged(positon);
    }

    // This method is for the set the toolbar .
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarId);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        subtitle = findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.backIconId);
        ImageButton save = toolbar.findViewById(R.id.save);

        // Listener for the save button.
        save.setOnClickListener(V -> saveStatus());

        title.setText(className);
        subtitle.setText(subjectName + "  |  " + calendar.getDate());

        back.setOnClickListener(V -> onBackPressed());

        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }

    private void saveStatus() {
        for (StudentItem studentItem : studentItems) {
            String status = studentItem.getStatus();
            if (status != "P") {
                status = "A";
            }
            long value = dbHelper.addStatus(studentItem.getSid(), cid, calendar.getDate(), status);
            if (value == -1) {
                dbHelper.updateStatus(studentItem.getSid(), calendar.getDate(), status);
            }
        }
        Toast.makeText(getApplicationContext(), "Present Complete", Toast.LENGTH_LONG).show();
    }

    private void loadStatusData() {
        for (StudentItem studentItem : studentItems) {
            // This is used to get the status form the database.
            String status = dbHelper.getStatus(studentItem.getSid(), calendar.getDate());

            if (status != null) {
                studentItem.setStatus(status);
            } else {
                studentItem.setStatus("");
            }
        }
        adapter.notifyDataSetChanged();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.add_student) {
            showAddStudentDialog();
        } else if (menuItem.getItemId() == R.id.showCalender) {
            showCalendar();
        } else if (menuItem.getItemId() == R.id.show_attendance_sheet) {
            openSheetList();
        }

        return true;
    }

    private void openSheetList() {

        long[] idArray = new long[studentItems.size()];
        String[] nameArray = new String[studentItems.size()];
        int[] rollArray = new int[studentItems.size()];

        // This part for taking the id into the idarray.
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = studentItems.get(i).getSid();
        }

        // This part is for taking the roll into the rollArray.
        for (int i = 0; i < rollArray.length; i++) {
            rollArray[i] = studentItems.get(i).getRoll();
        }

        // This part is for taking the name into the nameArray.
        for (int i = 0; i<nameArray.length;i++){
            nameArray[i]= studentItems.get(i).getName();
        }


        Intent intent = new Intent(this, SheetListActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArray);
        startActivity(intent);
    }

    // This method is used for show the calender.
    private void showCalendar() {
        calendar.show(getSupportFragmentManager(), "");
        calendar.setOnCalenderOkClickListener((this::onCalendarOkClicked));
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        subtitle.setText(subjectName + "  |  " + calendar.getDate());
        loadStatusData();
    }

    private void showAddStudentDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_ADD_DIALOG);
        dialog.setListener((roll, name) -> addStudent(roll, name));
    }

    private void addStudent(String roll_string, String name) {
        int roll = Integer.parseInt(roll_string);
        long sid = dbHelper.addStudent(cid, roll, name);
        StudentItem studentItem = new StudentItem(sid, roll, name);
        studentItems.add(studentItem);
        adapter.notifyDataSetChanged();
    }

    // This Method is used for the select the item when long press.
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getApplicationContext(), String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
                showUpdateStudentDialog(item.getGroupId());
                break;
            case 1:
                deleteStudent(item.getGroupId());  // Group id return the adapter position.
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateStudentDialog(int positon) {
        MyDialog dialog = new MyDialog(studentItems.get(positon).getRoll(), studentItems.get(positon).getName());
        dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_UPDATE_DIALOG);
        dialog.setListener((roll_string, name) -> updateStudent(positon, name));
    }

    private void updateStudent(int positon, String name) {

        dbHelper.updateStudent(studentItems.get(positon).getSid(), name);
        studentItems.get(positon).setName(name);
        adapter.notifyItemChanged(positon);
    }

    private void deleteStudent(int position) {
        // To Delete from database.
        dbHelper.deleteStudent(studentItems.get(position).getSid());

        // To delete from User Interface.
        studentItems.remove(position);
        adapter.notifyItemRemoved(position);

    }
}