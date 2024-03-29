package com.example.wewash.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewash.R;
import com.example.wewash.Splash1Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class A_SignUp extends AppCompatActivity implements View.OnClickListener {


    FirebaseAuth firebaseAuth;

    TextInputLayout Name1,Email1,Phone1,Password1,ConfirmPassword1;
    TextInputEditText Name,Email,Phone,Password,ConfirmPassword;
    Button SignUp;

    private static final Pattern USER_NAME_PATTERN= Pattern.compile("^[a-zA-Z ]{3,20}$",Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");
    private static final Pattern PHONE_NUMBER_PATTERN=Pattern.compile("^[9876][0-9]{9}$");



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth=FirebaseAuth.getInstance();

        Name1=findViewById(R.id.Sign_UP_Txt_Layout_1);
        Email1=findViewById(R.id.Sign_UP_Txt_Layout_2);
        Phone1=findViewById(R.id.Sign_UP_Txt_Layout_3);
        Password1=findViewById(R.id.Sign_UP_Txt_Layout_4);
        ConfirmPassword1=findViewById(R.id.Sign_UP_Txt_Layout_5);

        Name=findViewById(R.id.Sign_Up_Txt_EditTxt_Name);
        Email=findViewById(R.id.Sign_Up_Txt_EditTxt_Email);
        Phone=findViewById(R.id.Sign_Up_Txt_EditTxt_Phone);
        Password=findViewById(R.id.Sign_Up_Txt_EditTxt_Password);
        ConfirmPassword=findViewById(R.id.Sign_Up_Txt_EditTxt_Confirm_Password);

        SignUp=findViewById(R.id.Sign_up_Btn_SignUp_7);
        SignUp.setOnClickListener(this);


    }



    @Override
    public void onClick(View v){
        if (v.getId()==R.id.Sign_up_Btn_SignUp_7)
        {
            if (checkName(Name.getEditableText().toString()) && checkEmail(Email.getEditableText().toString()) && checkPhoneNumber(Phone.getEditableText().toString()) && checkPassword(Password.getEditableText().toString()) && checkConfirmPassword(Password.getEditableText().toString(),ConfirmPassword.getEditableText().toString()))
            {

                storeInFirebase();
                //startActivity(new Intent(this,A_SignIn.class));
            }
            else
            {

                Toast.makeText(getApplicationContext(),"Check Every Field",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void storeInFirebase()
    {
        firebaseAuth.createUserWithEmailAndPassword(Email.getEditableText().toString(),Password.getEditableText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            D_UserDataToStoreInFirebase user=new D_UserDataToStoreInFirebase(Name.getEditableText().toString(),Email.getEditableText().toString(),Password.getEditableText().toString(),Phone.getEditableText().toString());
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),"User Information stored in Database !!",Toast.LENGTH_SHORT).show();
                                        sendVerificationEmail();
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"Failed to store!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Something Wrong Failed to register",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void sendVerificationEmail()
    {
        firebaseAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Please Check Your Mail for verification!!",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed to send mail for verification",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }






    private boolean checkName(String Data)
    {
        if (TextUtils.isEmpty(Data)) {
            Name1.setError("name cannot be empty!!");
            return false;
        }
        else if(!USER_NAME_PATTERN.matcher(Data).matches())
        {
            Name1.setError("Not a Valid name!!");
            return false;
        }
        else
            return true;
    }

    private boolean checkEmail(String Data)
    {
        if (TextUtils.isEmpty(Data)) {
            Email1.setError("Cannot be empty!!");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(Data).matches())
        {
            Email1.setError("Not a Valid Email !!");
            return false;
        }
        else
        {
            //todo: check for email in database
            return true;
        }
    }

    private boolean checkPassword(String Data)
    {
        if (TextUtils.isEmpty(Data)) {
            Password1.setError("Password cannot be empty !!");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(Data).matches())
        {
            Password1.setError("Password is too weak !!");
            return false;
        }
        else
            return true;
    }

    private boolean checkConfirmPassword(String Data1,String Data2)
    {
        boolean flag=true;
        if (TextUtils.isEmpty(Data2))
        {
            ConfirmPassword1.setError("Confirm Password Cannot be empty");
            flag=false;
        }
        else if(!PASSWORD_PATTERN.matcher(Data2).matches())
        {
            ConfirmPassword1.setError("Password is too weak !!");
            flag=false;
        }
        else if (!Data1.contentEquals(Data2))
        {
            ConfirmPassword1.setError("Passwords are Not equal !!");
            flag=false;
        }
        return flag;
    }

    private boolean checkPhoneNumber(String Data)
    {
        if (TextUtils.isEmpty(Data)) {
            Phone1.setError("Phone number cannot be Blank !!");
            return false;
        }
        else if(!PHONE_NUMBER_PATTERN.matcher(Data).matches())
        {
            Phone1.setError("Invalid PhoneNumber !!");
            return false;
        }
        else
            return true;
    }


    public void backButton(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Splash1Activity.class);
        startActivity(i);
    }
}
