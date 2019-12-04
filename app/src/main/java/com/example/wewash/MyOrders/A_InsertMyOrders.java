package com.example.wewash.MyOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wewash.MainActivity;
import com.example.wewash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class A_InsertMyOrders extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout NameLayout,PhoneNumberLayout,EmailLayout,AddressLayout;
    TextInputEditText Name,Phone,Email,Address;
    TextView txtDate;
    Toolbar inserttool;
    Spinner NoOfClothes,PickUpTime,Area,Service;
    Button BookNow,PickUpDate;
    private int mYear, mMonth, mDay;
    String noOfClothes,mtime;
    String mArea,mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__insert_my_orders);
      findViewByIds();
    }

    private void findViewByIds()
    {
        NameLayout=findViewById(R.id.Txt_Input_Layout_MyOrders1);
        Name=findViewById(R.id.Txt_Input_EditText_Name);
        PhoneNumberLayout=findViewById(R.id.Txt_Input_Layout_MyOrders2);
        Phone=findViewById(R.id.Txt_Input_EditText_PhoneNumber);
        EmailLayout=findViewById(R.id.Txt_Input_Layout_MyOrders3);
        Email=findViewById(R.id.Txt_Input_EditText_Email);
        inserttool=findViewById(R.id.OrderToolbar);
        inserttool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        AddressLayout=findViewById(R.id.Txt_Input_Layout_MyOrders4);
        Address=findViewById(R.id.Address);
        PickUpDate=findViewById(R.id.PickUpDate);
        NoOfClothes=findViewById(R.id.total);
        PickUpTime=findViewById(R.id.time);
        Area=findViewById(R.id.area);
        Service=findViewById(R.id.service);
        BookNow=findViewById(R.id.booknow);
        BookNow.setOnClickListener(this);
        txtDate=findViewById(R.id.tv_date1);

        setSpinnerArea();
//        setSpinnerPickUpDate();
        setSpinnerPickTime();
        setSpinnerService();
        setSpinnerNoOfClothes();
    }


    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.booknow)
        {

          if (checkName(Name.getEditableText().toString()) && checkPhone(Phone.getEditableText().toString()) && checkEmail(Email.getEditableText().toString()) && checkAddress(Address.getEditableText().toString())  && checkNoOfClothes(noOfClothes) && checkDate()&& checkPickUpTime(mtime)&& checkService(mService))
          {
              final ProgressDialog progressDialog = new ProgressDialog(A_InsertMyOrders.this);
              progressDialog.setCancelable(false);
              progressDialog.setTitle("Placing your Order");
              progressDialog.setMessage("Your order is being placed please wait");
              progressDialog.show();
              DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyOrders");
              String ref=  databaseReference.push().getKey();
              DatabaseReference databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference("Admin").child("Orders").child("CurrentOrders");
              DatabaseReference databaseReferenceDisplay = FirebaseDatabase.getInstance().getReference("Admin").child("Orders").child("DisplayOrders");
             D_OrdersData dOrdersData=new D_OrdersData(Address.getEditableText().toString(),mArea,txtDate.getText().toString(),mtime,Name.getEditableText().toString(),noOfClothes,""+getDateTime(),Phone.getEditableText().toString(),"orderPlaced",0,""+ref,mService);


            databaseReference.child(ref).setValue(dOrdersData).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful())
                      {

                          Toast.makeText(getApplicationContext(), "order Placed Successfully ", Toast.LENGTH_LONG).show();
                          progressDialog.dismiss();
                           onBackPressed();
                      }
                      else

                          Toast.makeText(A_InsertMyOrders.this, "Unable to Place Order", Toast.LENGTH_SHORT).show();
                  }
              });
              databaseReferenceAdmin.child(ref).setValue(dOrdersData);
              databaseReferenceDisplay.child(ref).setValue(dOrdersData);
          }
        }
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private boolean checkName(String name) {
        if (!TextUtils.isEmpty(name)) {
            NameLayout.setError(null);
            return true;
        } else {
           NameLayout.setError("name cannot be empty!");
            return false;
        }
    }
    private boolean checkPhone(String phone) {
        if (phone.length()==10)
        {
            PhoneNumberLayout.setError(null);
            return true;
        }
        else
        {
            PhoneNumberLayout.setError("Some thing Wrong");
            return false;
        }
    }
    private boolean checkEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            EmailLayout.setError(null);
            return true;
        } else {
            EmailLayout.setError("Email cannot be empty!");
            return false;
        }
    }
    private boolean checkAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            AddressLayout.setError(null);
            return true;
        } else {
            AddressLayout.setError("Address cannot be empty!");
            return false;
        }
    }
    private boolean checkDate()
    {
        if (TextUtils.isEmpty(txtDate.getText().toString())){
            Toast.makeText(this, "Please Select the Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else

            return true;
    }
    private boolean checkNoOfClothes(String noofclothes)
    {
        if (TextUtils.isEmpty(noofclothes)) {
            Toast.makeText(A_InsertMyOrders.this, "Please select No.of Clothes", Toast.LENGTH_SHORT).show();
            return false;
        }
            else
                return true;
    }
    private boolean checkService(String mService) {
        if (TextUtils.isEmpty(mService)){
            Toast.makeText(this, "Please Select the type of Service", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }


    private boolean checkPickUpTime(String time) {
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(A_InsertMyOrders.this, "Please Select Time", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            mtime =time;
            return true;
        }
    }

   private void setSpinnerArea()
   {
       ArrayAdapter<CharSequence>arrayAdapter=ArrayAdapter.createFromResource(this,R.array.AreaList,android.R.layout.simple_spinner_item);
       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       Area.setAdapter(arrayAdapter);

       Area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mArea=parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
   }

   private void setSpinnerPickTime()
   {
       ArrayAdapter<CharSequence>arrayAdapter=ArrayAdapter.createFromResource(this,R.array.PickUpTime,android.R.layout.simple_spinner_item);
       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       PickUpTime.setAdapter(arrayAdapter);

       PickUpTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mtime=parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
   }


   private void setSpinnerNoOfClothes()
   {
       ArrayAdapter<CharSequence>arrayAdapter=ArrayAdapter.createFromResource(this,R.array.NoOfClothes,android.R.layout.simple_spinner_item);
       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      NoOfClothes.setAdapter(arrayAdapter);

       NoOfClothes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               noOfClothes=parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
   }
    private void setSpinnerService() {
        ArrayAdapter<CharSequence>arrayAdapter=ArrayAdapter.createFromResource(this,R.array.ServicesList,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Service.setAdapter(arrayAdapter);

        Service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mService=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void pickDate(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(A_InsertMyOrders.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                //PickUpDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+86400000);       //8640000 for a day in millis
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 345600000);    //604800000 for a week

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(A_InsertMyOrders.this, MainActivity.class));
    }
}
