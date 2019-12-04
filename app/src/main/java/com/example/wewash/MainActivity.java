package com.example.wewash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.wewash.LocateStore.LocateStore;
import com.example.wewash.Login.A_SignIn;
import com.example.wewash.MyOrders.A_DisplayMyProducts;
import com.example.wewash.MyOrders.A_InsertMyOrders;
import com.example.wewash.Profile.MyProfile;
import com.example.wewash.Profile.Profile;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     findViewById(R.id.InsertMyOrder).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(MainActivity.this, A_InsertMyOrders.class));
         }
     });

     findViewById(R.id.Displayproducts).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(MainActivity.this, A_DisplayMyProducts.class));
         }
     });
     findViewById(R.id.MyProfile).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(MainActivity.this, Profile.class));
         }
     });
     findViewById(R.id.ContactUs).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(MainActivity.this,ContactUs.class));
         }
     });
     findViewById(R.id.PriceList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PriceList.class));
            }
        });
     findViewById(R.id.LocateStore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LocateStore.class));
            }
        });
     findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?").setCancelable(false);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        clearCurrentUserData();
                        Intent intent=new Intent(getApplicationContext(), A_SignIn.class);
                        Toast.makeText(MainActivity.this, "Logged out Succesfully!", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
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
        });


    }


    public void ShareApp(View view) {
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT,"Hey I found an interesting app. I recommend" +
                " you to download and use it http://www.tinyurl.com/wewash");
        startActivity(Intent.createChooser(intent2,"Share via"));
    }

    public void Logout1(View view) {
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?").setCancelable(false);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                clearCurrentUserData();
                Intent intent=new Intent(getApplicationContext(), A_SignIn.class);
                Toast.makeText(MainActivity.this, "Logged out Succesfully!", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }

    public void aboutus(View view) {
        startActivity(new Intent(MainActivity.this,Aboutus.class));
    }
}
