package com.example.wewash.MyOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wewash.MyOrders.A_InsertMyOrders;

import com.example.wewash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class A_DisplayMyProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    String progressbar="";
    String UKey="";
    ArrayList<D_OrdersData> dOrdersDataArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__display_my_products);
        recyclerView=findViewById(R.id.RecyclerViewMyOrders);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        myAdapter=new MyAdapter(dOrdersDataArrayList);
        recyclerView.setAdapter(myAdapter);
        getDataFromFireBase();
        Toast.makeText(this, "Size :"+dOrdersDataArrayList.size(), Toast.LENGTH_SHORT).show();
    }
  private void getDataFromFireBase()
  {
      DatabaseReference dataKeyReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
      final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Admin").child("Orders").child("DisplayOrders");
      dataKeyReference.child("MyOrders").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren())
              {
                  if (dataSnapshot2.exists()) {
                      String key1 = dataSnapshot2.child("key").getValue(String.class);
                      databaseReference.child(key1).addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                  if (dataSnapshot1.exists()) {
                                      String address = dataSnapshot1.child("address").getValue(String.class);
                                      String area = dataSnapshot1.child("area").getValue(String.class);
                                      String dateBooked = dataSnapshot1.child("dateBooked").getValue(String.class);
                                      String expectedPickupTime = dataSnapshot1.child("expectedPickupTime").getValue(String.class);
                                      String name = dataSnapshot1.child("name").getValue(String.class);
                                      String noOfClothes = dataSnapshot1.child("noOfClothes").getValue(String.class);
                                      String orderId = dataSnapshot1.child("orderId").getValue(String.class);
                                      String phone = dataSnapshot1.child("phone").getValue(String.class);
                                      String status = dataSnapshot1.child("status").getValue(String.class);
                                      String progress = dataSnapshot1.child("progress").getValue(String.class);
                                      String key = dataSnapshot1.child("key").getValue(String.class);
                                      progressbar=progress;
                                      D_OrdersData displayOrders=new D_OrdersData(address, area,dateBooked, expectedPickupTime, name, noOfClothes, orderId, phone, status,progress,key);
                                      dOrdersDataArrayList.add(displayOrders);

                                  }

                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });

                  }

              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
      myAdapter.notifyDataSetChanged();
  }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderClass>
    {
        ArrayList<D_OrdersData> arrayList;
        MyAdapter(ArrayList<D_OrdersData> arrayList1)
        {
            this.arrayList=arrayList1;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new MyAdapter.ViewHolderClass(view);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

            holder.name.setText(arrayList.get(position).name);
            holder.address.setText(arrayList.get(position).address);
            holder.phone.setText(arrayList.get(position).phone);
            holder.date.setText(arrayList.get(position).dateBooked);
            holder.time.setText(arrayList.get(position).expectedPickupTime);
            holder.clothes.setText(arrayList.get(position).noOfClothes);
            holder.area.setText(arrayList.get(position).area);
            holder.id.setText(arrayList.get(position).orderId);
            holder.pb.setProgress(Integer.parseInt(progressbar));
        }

        private class ViewHolderClass extends RecyclerView.ViewHolder {
            TextView name, address, area, phone, clothes, date, time, id;
            ProgressBar pb;

             ViewHolderClass(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.tv_name);
                address = itemView.findViewById(R.id.tv_address);
                area = itemView.findViewById(R.id.tv_area);
                phone = itemView.findViewById(R.id.tv_phone);
                clothes = itemView.findViewById(R.id.tv_clothes);
                date = itemView.findViewById(R.id.tv_date);
                time = itemView.findViewById(R.id.tv_time);
                id = itemView.findViewById(R.id.tv_order);
                pb=itemView.findViewById(R.id.pb);

            }
        }
    }


}
