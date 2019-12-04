package com.example.wewash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wewash.Login.A_SignIn;
import com.example.wewash.Login.A_SignUp;

public class Splash1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

   }


    public void signup(View view) {

        Intent i = new Intent(this, A_SignUp.class);
        startActivity(i);
    }

    public void loginbtn(View view) {
        Intent i1 = new Intent(this, A_SignIn.class);
        startActivity(i1);
    }
}
