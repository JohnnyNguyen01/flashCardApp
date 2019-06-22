package com.example.triviaquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartingScreenActivity extends AppCompatActivity {
    private TextView titleTv;
    private TextView highScoreTv;
    private Button startQuizBtn;
    private Button quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen_activity);
        initialize();
    }

    /*
    *  Starts a new quiz Quiz_Activity activity
    * */
    private void startQuiz(){
        Intent intent = new Intent(getApplicationContext(),Quiz_Activity.class);
        startActivity(intent);
    }

    /*
    *  Initializes all fields
    * */
    private void initialize(){
        titleTv = findViewById(R.id.titleTV);
        highScoreTv = findViewById(R.id.highScoreTV);
        startQuizBtn = findViewById(R.id.startQuizBtn);
        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        quitBtn = findViewById(R.id.quitBtn);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}
