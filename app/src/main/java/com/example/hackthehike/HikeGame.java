
package com.example.hackthehike;

import static com.example.hackthehike.R.color.red;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HikeGame extends AppCompatActivity {
    Games s = new Games();
    Button scancode, submitans;
    TextView question;
    Button finish;
    boolean counter = false;
    int trueAnswer = 0;
    int choice;
    RadioButton group;
    TextView QH, AH;
    RadioButton[] options = new RadioButton[4];
    SqlScore Db;
    RadioGroup myRadioGroup;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"ResourceAsColor", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hike_game);
        submitans = (Button) findViewById(R.id.ans);
        question = (TextView) findViewById(R.id.question);
        scancode = (Button) findViewById(R.id.scan);
        finish = (Button) findViewById(R.id.finish);
        QH = (TextView) findViewById(R.id.qh);
        AH = (TextView) findViewById(R.id.ah);
        options[0] = findViewById(R.id.choice1);
        options[1] = findViewById(R.id.choice2);
        options[2] = findViewById(R.id.choice3);
        options[3] = findViewById(R.id.choice4);
        finish.setEnabled(true);
        submitans.setEnabled(false);

        Db = new SqlScore(this);
        myRadioGroup = (RadioGroup) findViewById(R.id.choicegroup);

        myRadioGroup.setVisibility(View.INVISIBLE);
        AH.setVisibility(View.INVISIBLE);
        QH.setVisibility(View.INVISIBLE);
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = myRadioGroup.findViewById(i);
                choice = myRadioGroup.indexOfChild(radioButton)+1;
                submitans.setEnabled(true);

                Log.e("Choice",choice+"///");



            }
        });


        // it submits the answer of the  user
        submitans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(" choice",choice+"///");
                Log.e("True choice",trueAnswer+"///");
                if (trueAnswer == choice) {
                    Log.e("Execute",trueAnswer+"///");
                    s.setScore(s.getScore() + 5);
                    Log.e("Score", s.getScore() + "");
                }

                setInvisible();
            }

            public void setInvisible() {
                AH.setVisibility(View.INVISIBLE);
                QH.setVisibility(View.INVISIBLE);
                submitans.setEnabled(false);
                question.setVisibility(View.INVISIBLE);
                myRadioGroup.setVisibility(View.INVISIBLE);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(HikeGame.this);

                builder.setMessage("Do you want to finish game \nYou would not be able to play again, Continue?");
                builder.setTitle("Finsh Game");


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                s.setFinish(true);
                                Intent intent = new Intent(HikeGame.this, Finish.class);
                                intent.putExtra("score", s.getScore());
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                builder.setPositiveButton("Yes", dialogClickListener);

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No",dialogClickListener);

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


//                    }
//                });

            }
        });

        scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HikeGame.this, QRScanner.class);
                i.putExtra("scores", s.getScore());

                i.putExtra("finish", s.isFinish());

                startActivity(i);

            }
        });
        // Extracting Resourses;
        Intent code = getIntent();

        s.setFinish(code.getBooleanExtra("finish", false));
        s.setScore(code.getIntExtra("myscore", 0));
        Log.e("Score", s.getScore() + "");
        if(!s.isFinish()) {
            String questionCode = code.getStringExtra("codes");
            if (scancode.getVisibility()==View.VISIBLE) {

                question.setText(questionCode);
                if (questionCode != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            msg("Question Timeout");
                            QH.setVisibility(View.INVISIBLE);
                            AH.setVisibility(View.INVISIBLE);
                            myRadioGroup.setVisibility(View.INVISIBLE);
                          // scancode.setVisibility(View.INVISIBLE);
                        }
                    }, 20000);
                    AH.setVisibility(View.VISIBLE);
                    QH.setVisibility(View.VISIBLE);
                    myRadioGroup.setVisibility(View.VISIBLE);

                    String[] choices = Questions.getChoices(questionCode);
                   // Toast.makeText(this,
                      //   "Choices"+choices[1]+"+==============>>",Toast.LENGTH_SHORT).show();
                    if (choices != null) {
                        boolean chk  = Db.InsetuserData(questionCode, 1);
                       // Toast.makeText(this,"DB Insert==="+chk,Toast.LENGTH_SHORT).show();
                        if (chk) {

                            for (int i = 0; i < 4; i++)
                                options[i].setText(choices[i]);
                            trueAnswer = Questions.getLastchoice();
                            Log.e("True choice", trueAnswer + "///");
                        } else
                            msg("Question  already answered!");
                    } else
                        msg("Invalid QR Code scanned.");
                    finish.setEnabled(true);
                }
            } else
                msg("\t\t Failed To Load Question");
        }else
            msg("\t\tYou Have Finished Game!");

    }




    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int a = sh.getInt("scores", 0);
        s.setFinish(sh.getBoolean("finish",  false));
        s.setScore(a);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("scores", s.getScore());
        myEdit.putBoolean("finish", s.isFinish());
        myEdit.apply();


    }

    public void msg(String msg) {
        question.setText(msg);
        question.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.siez_3));


        QH.setVisibility(View.INVISIBLE);
        AH.setVisibility(View.INVISIBLE);
        myRadioGroup.setVisibility(View.INVISIBLE);

    }


}
