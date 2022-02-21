package com.example.hackthehike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Instruction extends AppCompatActivity {
    TextView inst;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        inst=(TextView) findViewById(R.id.instruction);
        next=(Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Instruction.this,HikeGame.class);
                startActivity(i);
                setResult(1);
                finish();
            }
        });

        inst.setText("Read these Instruction Carefully \n1. Discipline is to be maintained throughout  hike/event \n 2. QR code contains question, correct answer points will be added and one clicking Finish Score will be displayed to you \n 3. QR Code can only be scanned once and a total of 20 seconds will be given to you to answer it.\n4. Winner will be that person who answers the most questions and reaches the top first. Reaching first will also give you an additional 20 points.");
       // inst.setText("Read these Instruction Carefully \n1. Discipline is to be maintained throughout the duration of the hike/event.\n2.2. QR Code contains a question. In case of correct answer,points will be added and once you click “Finish”, total   will be calculated and displayed to you\n3. Once QR is Scanned it can not be \nre scanned\n4. Winner is who have more points and reached first ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}