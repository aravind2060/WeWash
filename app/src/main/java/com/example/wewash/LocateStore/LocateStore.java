package com.example.wewash.LocateStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.wewash.MainActivity;
import com.example.wewash.R;

public class LocateStore extends AppCompatActivity {
    Toolbar locateTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_store);
        locateTool=findViewById(R.id.LocateTool);
        locateTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void LocateMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<31.253859>,<75.698229>?q=<31.253859>,<75.698229>(Shiv Laundry)"));
        startActivity(intent);
    }

    public void callstore(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9041577549"));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LocateStore.this, MainActivity.class));
    }
}
