package com.example.tianhao.twitter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    ArrayList<String> users = new ArrayList<>();
    ArrayList<String> usersName = new ArrayList<>();
    ArrayAdapter adapter;
    private FirebaseAuth mAuth;
    String currentEmail;
    FirebaseUser currentUser;
    String[] currentLogINUser;
    User user;
    String[] names = {"John","Tim","Sam","Ben"};
    List nameList = new ArrayList<String>(Arrays.asList(names));
    List hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setTitle("User List");

        user = new User();
        ListView listView = findViewById(R.id.userListView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentEmail = currentUser.getEmail();
        currentLogINUser = currentEmail.split("\\.");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()) {
                    Log.i("Info", "Checked!");
                    //FirebaseDatabase.getInstance().getReference().child("users").child(currentLogINUser[0]).child("isFollowing").setValue(nameList);
                   // Log.i("inside hello",String.valueOf(hello.get(0)));
                    //Log.i("name list",String.valueOf(nameList.get(0)));
                } else {
                    Log.i("Info", "Not Checked!");
                }
            }
        });



        FirebaseDatabase.getInstance().getReference().child("users").child("a@b").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot emailFireBase : dataSnapshot.getChildren()) {

                        String usera = dataSnapshot.child("email").getValue(String.class);
                        //user = emailFireBase.getValue(User.class);
                        users.add(usera);
                        //hello = user.getFollowing();
                        //users.add(String.valueOf( hello.get(0)));
                        //Log.i("receive message",String.valueOf(hello.get(0)));
                        //usersName.add(tempUserName[0]);

                    //Log.i("receive message",String.valueOf(nameList.get(1)));
                }
                for (DataSnapshot childSnapshot: dataSnapshot.child("is Following").getValue(String.class)) {
                    System.out.println(childSnapshot.getValue(String.class));
                    Log.i("whats inside",childSnapshot.getValue(String.class));
                    hello = Collections.singletonList(childSnapshot.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        //Log.i("name list",String.valueOf(nameList.get(0)));
    }

}
