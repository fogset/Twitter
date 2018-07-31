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
    UsersTweets mApp;
     ArrayList<String> usersTweets = new ArrayList<>();
     ArrayList<String> usersEmail = new ArrayList<>();
    ArrayAdapter adapter;
    SharedPreferences sharedPreferences;
    String entireTweets ;
    String entireEmailTweets;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        user = new User();
        mApp = (( UsersTweets)getApplicationContext());

        final ListView listView = findViewById(R.id.listView);
        final List<Map<String, String>> tweetData = new ArrayList<>();
        final Map<String, String> tweetInfo = new HashMap<>();
        sharedPreferences = this.getSharedPreferences("com.example.tianhao.twitter", Context.MODE_PRIVATE);


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
                }

                sharedPreferences.edit().putString("userTweets", entireTweets).apply();
                sharedPreferences.edit().putString("userTweetsEmail", entireEmailTweets).apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//        for (int i = 0; i<3; i++){
//            //tweetInfo.put("content", usersTweets.get(i));
//            //tweetInfo.put("username", usersEmail.get(i));
//            Log.i("tweets", usersTweets.get(i));
//            Log.i("email", usersEmail.get(i));
//            //tweetData.add(tweetInfo);
//            //simpleAdapter.notifyDataSetChanged();
//        }
        //String wholeString = sharedPreferences.getString("wholeString","");
        //simpleAdapter.notifyDataSetChanged();
        //Log.i("emailaooo", wholeString);

        //Log.i("second tweets outside class", String.valueOf(((UsersTweets)getApplicationContext()).getUsersTweets()));
        Log.i("global", mApp.getGlobalVarValue());
        Log.i("globalaa", "yes");
        for (int i = 1; i <= 5; i++) {
            tweetInfo.put("content", "Tweet Content" + Integer.toString(i));
            tweetInfo.put("username", "User" + Integer.toString(i));
            tweetData.add(tweetInfo);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        new CountDownTimer(2000,1000){
          public  void onTick(long millisecondsUntilDone){
          }
          public void onFinish(){
              Log.i("We are done!", "No more countdown");
              entireTweets = sharedPreferences.getString("userTweets","");
              entireEmailTweets = sharedPreferences.getString("userTweetsEmail","");
              Log.i("emailLList", entireEmailTweets);
              Log.i("emailLTweet", entireTweets);
          }
        }.start();



//                for (int i = 0; i<3; i++){
//            //tweetInfo.put("content", usersTweets.get(i));
//            //tweetInfo.put("username", usersEmail.get(i));
//            Log.i("tweets", usersTweets.get(i));
//            Log.i("email", usersEmail.get(i));
//            //tweetData.add(tweetInfo);
//            //simpleAdapter.notifyDataSetChanged();
//        }
    }




}
