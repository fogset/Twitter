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
    //String[] usersTweets,usersEmail;
    ArrayList<String> usersTweets = new ArrayList<>();
    ArrayList<String> usersEmail = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String entireTweets ;
    String entireEmailTweets;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        user = new User();
        sharedPreferences = this.getSharedPreferences("com.example.tianhao.twitter", Context.MODE_PRIVATE);

        ListView listView = findViewById(R.id.listView);
        final List<Map<String, String>> tweetData = new ArrayList<>();

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, tweetData, android.R.layout.simple_list_item_2, new String[]{"content", "username"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);


        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String entireTweets = "";
                String entireEmailTweets = "";
                for (DataSnapshot emailFireBase : dataSnapshot.child("emailList").getChildren()) {
                    user = emailFireBase.getValue(User.class);
                    entireTweets = entireTweets + "#" + user.getTweets();
                    entireEmailTweets = entireEmailTweets + "#" + user.getEmail();
                    usersTweets.add(user.getTweets());
                    usersEmail.add(user.getEmail());
                }
                for (int a = 0; a<4; a++) {
                    Map<String, String> tweetInfo = new HashMap<>();
                    tweetInfo.put("content",  usersTweets.get(a));
                    tweetInfo.put("username",  usersEmail.get(a));
                    tweetData.add(tweetInfo);
                }
                simpleAdapter.notifyDataSetChanged();
                sharedPreferences.edit().putString("userTweets", entireTweets).apply();
                sharedPreferences.edit().putString("userTweetsEmail", entireEmailTweets).apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();


        new CountDownTimer(1000,1000){
          public  void onTick(long millisecondsUntilDone){
          }
          public void onFinish(){
              entireTweets = sharedPreferences.getString("userTweets","");
              entireEmailTweets = sharedPreferences.getString("userTweetsEmail","");
              Log.i("emailLList", entireEmailTweets);
              Log.i("emailLTweet", entireTweets);
              usersTweets= entireTweets.split("\\#");
              usersEmail= entireEmailTweets.split("\\#");
              for (int i = 1; i <usersTweets.length; i++) {
                  Map<String, String> tweetInfo = new HashMap<>();
                  tweetInfo.put("content",  usersTweets[i] );
                  tweetInfo.put("username",  usersEmail[i]);
                  tweetData.add(tweetInfo);
              }
              simpleAdapter.notifyDataSetChanged();
          }
        }.start();
        for (int i = 0; i <= 3; i++) {
            tweetInfo.put("content", "Tweet Content" +String.valueOf(i) );
            tweetInfo.put("username", "User" + String.valueOf(i));
            tweetData.add(tweetInfo);
        }
        simpleAdapter = new SimpleAdapter(FeedActivity.this, tweetData, android.R.layout.simple_list_item_2, new String[]{"content", "username"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);

        for (int i = 0; i <= 2; i++) {
            tweetInfo.put("content", "Tweet Content" +usersTweets[i] );
            tweetInfo.put("username", "User" + usersEmail[i]);
            tweetData.add(tweetInfo);
        }








    }




}
