package com.rafi.attendecetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

public class SplashScreen extends AppCompatActivity {

    ImageView logoImage;
    TextView Slogan1,Slogan2;
    Animation topAnimation,bottomAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logoImage = findViewById(R.id.logoImage);
        Slogan1 = findViewById(R.id.solganId);
        Slogan2 = findViewById(R.id.solganId1);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoImage.setAnimation(topAnimation);
        Slogan1.setAnimation(bottomAnimation);
        Slogan2.setAnimation(bottomAnimation);
        //This try and catch block is for the android.database.sqlite.SQLiteBlobTooBigException: Row too big to fit into CursorWindow requiredPos=0, totalRows=1...
        // This code is copy from the stack Overflow Link =https://stackoverflow.com/questions/51959944/sqliteblobtoobigexception-row-too-big-to-fit-into-cursorwindow-requiredpos-0-t
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }


        int SPLASH_SCREEN=4300;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginAcrivity.class);
                startActivity(intent);
                finish(); // this allow not to back this screen if back button is clicked.
            }
        },SPLASH_SCREEN);
    }
}