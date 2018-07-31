package com.example.tianhao.twitter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
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
    ListView listView;
    ArrayList<String> usersTweets = new ArrayList<>();
    ArrayList<String> usersEmail = new ArrayList<>();
    String passedIsFollowing;
    SharedPreferences sharedPreferences;
    String [] FollwingList;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        user = new User();
        ListView listView = findViewById(R.id.listView);
        final List<Map<String, String>> tweetData = new ArrayList<>();

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, tweetData, android.R.layout.simple_list_item_2, new String[]{"content", "username"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        sharedPreferences = this.getSharedPreferences("com.example.tianhao.twitter", Context.MODE_PRIVATE);
        passedIsFollowing = sharedPreferences.getString("userTweetsEmail","");
        //Log.i("isFollowing", String.valueOf(passedIsFollowing));
        FollwingList = passedIsFollowing.split("\\#");
        for (int i = 0; i < FollwingList.length; i++) {
            Log.i("Split", FollwingList[i]);
        }

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot emailFireBase : dataSnapshot.child("emailList").getChildren()) {
                    user = emailFireBase.getValue(User.class);
                    usersTweets.add(user.getTweets());
                    usersEmail.add(user.getEmail());
                }
//                for (int i = 0; i<4; i++) {
//                    Map<String, String> tweetInfo = new HashMap<>();
//                    tweetInfo.put("content",  usersTweets.get(i));
//                    tweetInfo.put("username",  usersEmail.get(i));
//                    tweetData.add(tweetInfo);
//                }
                for (String following: FollwingList){
                    if(usersEmail.contains(following)){
                        Map<String, String> tweetInfo = new HashMap<>();
                        tweetInfo.put("content",  usersTweets.get(usersEmail.indexOf(following)));
                        tweetInfo.put("username",  usersEmail.get(usersEmail.indexOf(following)));
                        tweetData.add(tweetInfo);

                    }
                }
                simpleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


    }

}
