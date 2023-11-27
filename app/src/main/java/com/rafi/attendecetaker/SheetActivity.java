package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.DbHelper;

import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {
    Toolbar toolbar;
    String month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);

        month = getIntent().getStringExtra("month");

        setToolbar();

        // This method is used for the show the table.
        showTable();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarId);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.backIconId);
        ImageButton save = toolbar.findViewById(R.id.save);

        // Listener for the save button.
        save.setVisibility(View.GONE);
        int value  = Integer.parseInt(month.substring(0,2));

        if (value == 01)
            title.setText("January");
        else if (value == 2)
            title.setText("February");
        else if (value == 3)
            title.setText("March");
        else if (value == 4)
            title.setText("April");
        else if (value == 5)
            title.setText("May");
        else if (value == 6)
            title.setText("June");
        else if (value == 6)
            title.setText("July");
        else if (value == 8)
            title.setText("August");
        else if (value == 9)
            title.setText("September");
        else if (value == 10)
            title.setText("October");
        else if (value == 11)
            title.setText("November");
        else
            title.setText("December");

        subtitle.setText(month.substring(3));

        back.setOnClickListener(V -> onBackPressed());
    }

    private void showTable() {
        DbHelper dbHelper = new DbHelper(this);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        long [] idArray = getIntent().getLongArrayExtra("idArray");
        int [] rollArray = getIntent().getIntArrayExtra("rollArray");
        String [] nameArray = getIntent().getStringArrayExtra("nameArray");

        // This method return how many day in a particular month to create the table in perfect.
        int DAY_IN_MONTH = getDayInMonth(month);


        // Setup the rol for the table.
        int rollSize = (idArray.length+1);
        TableRow[] rows = new TableRow[rollSize];
        TextView[] roll_tvs = new TextView[rollSize];
        TextView[] name_tvs = new TextView[rollSize];
        TextView[][] status_tvs= new TextView[rollSize][DAY_IN_MONTH+1];

        for (int i=0;i<rollSize;i++){
            roll_tvs[i] = new TextView(this);
            name_tvs[i] = new TextView(this);
            for (int j =1;j<=DAY_IN_MONTH;j++){
                status_tvs[i][j]=new TextView(this);
            }
        }

        //This portion is used for the header.
        roll_tvs[0].setText("Roll");
        // This two line is used for bold the header .
        roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setText("Name");

        for (int i = 1; i <= DAY_IN_MONTH; i++) {
            status_tvs[0][i].setText(i+"");
            status_tvs[0][i].setTypeface(status_tvs[0][i].getTypeface(), Typeface.BOLD);
        }

        for (int i = 1; i < rollSize; i++) {
            roll_tvs[i].setText(String.valueOf(rollArray[i-1]));
            name_tvs[i].setText(nameArray[i-1]);

            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                String day = String.valueOf(j);
                if (day.length()==1){
                    day ="0"+day;
                }
                String date = day+"/"+month;
                String status = dbHelper.getStatus(idArray[i-1],date);
                status_tvs[i][j].setText(status);
            }
        }

        for (int i = 0; i < rollSize; i++) {
            rows[i] = new TableRow(this);

            // This is used to set the color in table row.
            if (i%2==0){
                rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            }else{
                rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));
            }

            // This two line is for giving the padding.
            roll_tvs[i].setPadding(25,25,25,25);
            name_tvs[i].setPadding(25,25,25,25);

            rows[i].addView(roll_tvs[i]);
            rows[i].addView(name_tvs[i]);
            for (int j = 1; j <= DAY_IN_MONTH; j++) {

                status_tvs[i][j].setPadding(25,25,25,25);

                rows[i].addView(status_tvs[i][j]);
            }
            tableLayout.addView(rows[i]);
        }
        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);

    }

    private int getDayInMonth(String month) {
        int monthIndex = Integer.valueOf(month.substring(0,1));
        int year = Integer.valueOf(month.substring(4));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthIndex);
        calendar.set(Calendar.YEAR,year);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}