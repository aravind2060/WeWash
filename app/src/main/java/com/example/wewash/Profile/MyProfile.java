package com.example.wewash.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wewash.D_CurrentUser;
import com.example.wewash.Login.A_SignIn;
import com.example.wewash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfile extends AppCompatActivity {
    TextInputLayout Up_Name2,Up_Email2,Up_Phone2,Up_Password2;
    TextInputEditText Up_Name,Up_Email,Up_Phone,Up_Password;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        firebaseAuth=FirebaseAuth.getInstance();

        Up_Name2=findViewById(R.id.Up_Txt_Layout_1);
        Up_Email2=findViewById(R.id.Up_Txt_Layout_2);
        Up_Phone2=findViewById(R.id.Up_Txt_Layout_3);
        //Up_Password2=findViewById(R.id.Up_Txt_Layout_4);
        toolbar=findViewById(R.id.Toolbar_Update_page);
        Up_Name=findViewById(R.id.Update_Name);
        Up_Email=findViewById(R.id.Update_Email);
        Up_Phone=findViewById(R.id.Update_Phone);
        //Up_Password=findViewById(R.id.Update_pass);


        Up_Name.setText(D_CurrentUser.getName());
        Up_Phone.setText(D_CurrentUser.getPhoneNumber());
        Up_Email.setText(D_CurrentUser.getEmail());

    }

    public void UpdateProfile(View view) {


        String name = Up_Name.getText().toString();
        String phone = Up_Phone.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Enter the Phone", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.child("Name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(MyProfile.this, "Name updated successsfully", Toast.LENGTH_SHORT).show();
                }
            });
            databaseReference.child("PhoneNumber").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(MyProfile.this, "PhoneNumber updated successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void DeleteProfile(View view) {
                String uid=firebaseUser.getUid();
                clearAll();
                deleteuserData(uid);
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Account deleted successfully", Toast.LENGTH_LONG).show();
                            removeCurrentUser();
                            Intent intent=new Intent(MyProfile.this, A_SignIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
            }
        public void clearAll()
        {
            D_CurrentUser.setName("");
            D_CurrentUser.setEmail("");
            D_CurrentUser.setPhoneNumber("");
        }

        public void deleteuserData(String uid)
        {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

            databaseReference.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(getApplicationContext(),"Your application data deleted successfully",Toast.LENGTH_SHORT).show();
                    Log.e("A_Account","Data deleted");
                }
            });
        }
        public void removeCurrentUser()
        {
            firebaseAuth.updateCurrentUser(null);
            Toast.makeText(getApplicationContext(),"Current user set to null",Toast.LENGTH_LONG).show();
            Log.e("A_Account","Current user set to null");
        }

    }

