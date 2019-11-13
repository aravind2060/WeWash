package com.example.wewash.LocateStore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.wewash.R;

public class LocateStore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_store);


    }

    public void LocateMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<31.253859>,<75.698229>?q=<31.253859>,<75.698229>(Shiv Laundry)"));
        startActivity(intent);
    }
}
