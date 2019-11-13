package com.example.wewash.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wewash.D_CurrentUser;
import com.example.wewash.Login.A_SignIn;
import com.example.wewash.R;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    TextView profileName;
    TextView profilePhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileName = findViewById(R.id.Profile_Name);
        profilePhoneNumber=findViewById(R.id.Profile_phone);

        profileName.setText(D_CurrentUser.getName());
        profilePhoneNumber.setText(D_CurrentUser.getPhoneNumber());
    }

    public void MyProfile(View view) {
        Intent intent = new Intent(Profile.this,MyProfile.class);
        startActivity(intent);
    }

    public void Logout(View view) {
            FirebaseAuth.getInstance().signOut();
            clearCurrentUserData();
            Intent intent=new Intent(getApplicationContext(), A_SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    private void clearCurrentUserData()
    {
        D_CurrentUser.setPhoneNumber(null);
        D_CurrentUser.setName(null);
        D_CurrentUser.setEmail(null);
    }


}