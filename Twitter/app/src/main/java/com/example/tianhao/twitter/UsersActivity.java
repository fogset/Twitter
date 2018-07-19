package com.example.tianhao.twitter;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.GenericTypeIndicator;
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
    List<String> nameList = new ArrayList();
    Boolean repeatedFollowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setTitle("User List");

        user = new User();
        final ListView listView = findViewById(R.id.userListView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentEmail = currentUser.getEmail();
        currentLogINUser = currentEmail.split("\\.");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);
        listView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot emailFireBase : dataSnapshot.child("emailList").getChildren()) {
                    user = emailFireBase.getValue(User.class);
                    users.add(user.getEmail());
                }
                nameList= dataSnapshot.child(currentLogINUser[0]).child("isFollowing").getValue(new GenericTypeIndicator<List<String>>(){});
                Log.i("inside namelist is ", String.valueOf(nameList));
                for (String username: nameList){
                    if(users.contains(username)){
                        listView.setItemChecked(users.indexOf(username), true);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()) {
                    Log.i("Info", "Checked!");
                    for (int s = 0; s<nameList.size(); s++){
                        if(users.get(position).equals(nameList.get(s))){
                            Log.i("sss inside nameList", String.valueOf(nameList.get(s)));
                            repeatedFollowing = true;
                            Log.i("repeatedFollowing", String.valueOf(repeatedFollowing));
                        }
                    }
                    if(repeatedFollowing == false){
                        nameList.add(users.get(position));
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentLogINUser[0]).child("isFollowing").setValue(nameList);
                    }
                    repeatedFollowing = false;

                } else {
                    Log.i("Info", "Not Checked!");
                    nameList.remove(users.get(position));
                    FirebaseDatabase.getInstance().getReference().child("users").child(currentLogINUser[0]).child("isFollowing").setValue(nameList);
                }
            }
        });



    }

}
