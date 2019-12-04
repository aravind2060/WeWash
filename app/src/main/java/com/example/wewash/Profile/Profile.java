package com.example.wewash.Profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wewash.D_CurrentUser;
import com.example.wewash.Login.A_SignIn;
import com.example.wewash.MainActivity;
import com.example.wewash.MyOrders.A_DisplayMyProducts;
import com.example.wewash.R;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    TextView profileName;
    TextView profilePhoneNumber;
    Toolbar ProfileTool;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileName = findViewById(R.id.Profile_Name);
        profilePhoneNumber=findViewById(R.id.Profile_phone);
        ProfileTool=findViewById(R.id.Toolbar_Profile_Page);
        ProfileTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        profileName.setText(D_CurrentUser.getName());
        profilePhoneNumber.setText(D_CurrentUser.getPhoneNumber());
    }

    public void MyProfile(View view) {
        Intent intent = new Intent(Profile.this,MyProfile.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?").setCancelable(false);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                clearCurrentUserData();
                Intent intent = new Intent(getApplicationContext(), A_SignIn.class);
                Toast.makeText(Profile.this, "Logged out Succesfully!", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Profile.this,Profile.class));
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void clearCurrentUserData()
    {
        D_CurrentUser.setPhoneNumber(null);
        D_CurrentUser.setName(null);
        D_CurrentUser.setEmail(null);
    }


    public void DisplayOrders(View view) {
        startActivity(new Intent(Profile.this, A_DisplayMyProducts.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Profile.this,MainActivity.class));
    }
}