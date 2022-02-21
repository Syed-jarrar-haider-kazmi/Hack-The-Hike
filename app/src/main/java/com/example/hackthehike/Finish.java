package com.example.hackthehike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Finish extends AppCompatActivity {

    Button score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        score=(Button) findViewById(R.id.scored);

        Intent i =getIntent();
        int s =i.getIntExtra("score",0);
        score.setText("\t\t\t    Your Score \n\n\n  "+ s+"/50");


    }
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}