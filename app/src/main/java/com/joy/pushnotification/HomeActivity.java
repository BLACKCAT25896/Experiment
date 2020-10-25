package com.joy.pushnotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joy.pushnotification.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private DatabaseReference databaseReference;
    private List<String> userNameList;
    private List<String> userIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        init();

        getAllUsers();

    }

    private void initListView() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,userNameList);
        binding.listView.setAdapter(arrayAdapter);
    }

    private void getAllUsers() {
        DatabaseReference userRef = databaseReference.child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    userNameList.clear();
                    userIds.clear();
                    for (DataSnapshot data: snapshot.getChildren()){
                        String name = data.child("name").getValue().toString();
                        String userId = data.getKey();
                        userNameList.add(name);
                        userIds.add(userId);
                    }
                    initListView();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userNameList = new ArrayList<>();
        userIds = new ArrayList<>();
    }
}