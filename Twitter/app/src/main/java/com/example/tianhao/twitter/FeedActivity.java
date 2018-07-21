package com.example.tianhao.twitter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    User user;
    ArrayList<String> usersTweets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        user = new User();
        final ListView listView = findViewById(R.id.listView);
        final List<Map<String, String>> tweetData = new ArrayList<>();
        final Map<String, String> tweetInfo = new HashMap<>();

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, tweetData, android.R.layout.simple_list_item_2, new String[]{"content", "username"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);


        FirebaseDatabase.getInstance().getReference().child("users").child("tweetList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersTweets.clear();
                for (DataSnapshot emailFireBase : dataSnapshot.getChildren()) {
                    user = emailFireBase.getValue(User.class);
                    //usersTweets.add(user.getEmail());
                    tweetInfo.put("content", user.getTweets());
                    tweetInfo.put("username", "a@b.com");
                    Log.i("tweets", user.getTweets());
                    tweetData.add(tweetInfo);
                    simpleAdapter.notifyDataSetChanged();

                }
//                nameList= dataSnapshot.child(currentLogINUser[0]).child("isFollowing").getValue(new GenericTypeIndicator<List<String>>(){});
//                Log.i("inside namelist is ", String.valueOf(nameList));
//                for (String username: nameList){
//                    if(users.contains(username)){
//                        listView.setItemChecked(users.indexOf(username), true);
//                    }
//                }
                //adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

//        for (int i = 1; i <= 5; i++) {
//            tweetInfo.put("content", "Tweet Content" + Integer.toString(i));
//            tweetInfo.put("username", "User" + Integer.toString(i));
//            tweetData.add(tweetInfo);
//        }
    }

}
