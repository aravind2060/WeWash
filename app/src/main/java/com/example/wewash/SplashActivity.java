package com.example.wewash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2*1000);
                    Intent i = new Intent(getApplicationContext(),Splash1Activity.class);
                    startActivity(i);

                    finish();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }
}
