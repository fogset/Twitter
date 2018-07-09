package com.example.tianhao.text1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){

        EditText amount = (EditText)findViewById(R.id.amount);
        String amountInusd = amount.getText().toString();
        Double amountInyuan = Double.parseDouble(amountInusd) * 6.35;
        String yuan = String.format("%.2f", amountInyuan);

       Toast.makeText(this, "Amount in yuan: $" + yuan, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
