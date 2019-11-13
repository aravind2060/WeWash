package com.example.wewash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }


    public void EmailUs(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "harrythalanki@gmail.com");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public void CallUs(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:7981042604"));
        startActivity(intent);
    }
    public void LocateUs(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<31.248941>,<75.700130>?q=<31.248941>,<75.700130>(Label+WeWash)"));
        startActivity(intent);

    }

}
