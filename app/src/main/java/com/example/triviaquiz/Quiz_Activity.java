package com.example.triviaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/*
* TODO: https://www.youtube.com/watch?v=tlgrX3HF6AI <-- next video
*
* */
public class Quiz_Activity extends AppCompatActivity {
    private TextView questionTV;
    private TextView countDownTV;
    private TextView scoreTV;
    private TextView questionCountTV;
    private Button confirmBtn;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private ArrayList<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_);
        initialize();
    }

    private void initialize(){
        questionTV = findViewById(R.id.questionTV);
        countDownTV = findViewById(R.id.countDownTV);
        scoreTV = findViewById(R.id.scoreTV);
        questionCountTV = findViewById(R.id.questionCountTV);
        confirmBtn = findViewById(R.id.confirmBtn);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        QuizDBHelper dbHelper = new QuizDBHelper(getApplicationContext());
        questionsList = dbHelper.getAllQuestions();
    }
}
