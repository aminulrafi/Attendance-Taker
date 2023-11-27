package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.Constant;
import com.rafi.attendecetaker.Database.DbHelper;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ListView sheetList;
    private ArrayAdapter adapter;
    private ArrayList<String> listItems = new ArrayList();
    private long cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        cid = getIntent().getLongExtra("cid",-1);

        setToolbar();

        //This method is used for load the data to the list item.
        loadListItems();


        sheetList = findViewById(R.id.sheetList);
        adapter = new ArrayAdapter(this,R.layout.sheet_list,R.id.date_list_item,listItems);
        sheetList.setAdapter(adapter);

        // If any of the item is clicked on the sheetList Activity then this on item click listener is work.
        sheetList.setOnItemClickListener((parent, view, position, id) -> openSheetActivity(position));
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarId);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.backIconId);
        ImageButton save = toolbar.findViewById(R.id.save);

        // Listener for the save button.
        save.setVisibility(View.GONE);

        title.setText("SheetList");
        subtitle.setText("All Month Attendence");

        back.setOnClickListener(V -> onBackPressed());

        //toolbar.inflateMenu(R.menu.student_menu);
        //toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }

    private void openSheetActivity(int position) {
        // Catache the value from the intent that was pass.
        long [] idArray = getIntent().getLongArrayExtra("idArray");
        int [] rollArray = getIntent().getIntArrayExtra("rollArray");
        String [] nameArray = getIntent().getStringArrayExtra("nameArray");

        Intent intent = new Intent(this,SheetActivity.class);
        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArray);
        intent.putExtra("month",listItems.get(position));
        startActivity(intent);
    }

    private void loadListItems() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);

        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(Constant.DATE_KEY));
            listItems.add(date.substring(3));
        }
    }
}