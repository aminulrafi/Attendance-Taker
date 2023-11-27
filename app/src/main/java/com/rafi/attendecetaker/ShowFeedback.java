package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rafi.attendecetaker.Database.DbHelper;
import com.rafi.attendecetaker.RecyclerView.FeedbackAdapter;

import java.util.ArrayList;

public class ShowFeedback extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<FeedbackRetrives> feedBackItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feedback);

        // Toolbar is call here.
        setToolbar();

        recyclerView = findViewById(R.id.feedBackRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        feedBackItems = new ArrayList<>();
        Cursor cursor = new DbHelper(getApplicationContext()).retriveFeedback();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String feedBack = cursor.getString(7);
                String occupation = cursor.getString(6);
                byte[] img = cursor.getBlob(4);
                Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
                feedBackItems.add(new FeedbackRetrives(name, feedBack, image,occupation));
            }
        }
        recyclerView.setAdapter(new FeedbackAdapter(feedBackItems));
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarId);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.backIconId);
        ImageButton save = toolbar.findViewById(R.id.save);


        title.setText("All Comments");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        back.setOnClickListener(V -> onBackPressed());

    }
}