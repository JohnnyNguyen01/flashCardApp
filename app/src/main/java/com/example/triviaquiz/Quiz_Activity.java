package com.example.triviaquiz;

import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

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
    private RadioButton option1RB;
    private RadioButton option2RB;
    private RadioButton option3RB;
    private RadioButton option4RB;

    private ColorStateList textColourDefaultRB; //save default RB text color to change later according to correct answer

    private ArrayList<Question> questionsList;

    private int questionCounter;
    private int questionTotalCount;
    private Question currentQuestion;
    private int score;
    private boolean answered;

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
        option1RB = findViewById(R.id.option1);
        option2RB = findViewById(R.id.option2);
        option3RB = findViewById(R.id.option3);
        option4RB = findViewById(R.id.option4);

        textColourDefaultRB = option1RB.getTextColors();

        QuizDBHelper dbHelper = new QuizDBHelper(getApplicationContext());
        questionsList = dbHelper.getAllQuestions();
        questionTotalCount = questionsList.size();
        Collections.shuffle(questionsList); // randomizes appearance of questions
        showNextQuestion();
    }

    private void showNextQuestion(){
        option1RB.setTextColor(textColourDefaultRB);
        option2RB.setTextColor(textColourDefaultRB);
        option3RB.setTextColor(textColourDefaultRB);
        option4RB.setTextColor(textColourDefaultRB);

        if(questionCounter < questionTotalCount){
            currentQuestion = questionsList.get(questionCounter);

            questionTV.setText(currentQuestion.getQuestion());
            option1RB.setText(currentQuestion.getOption1());
            option2RB.setText(currentQuestion.getOption2());
            option3RB.setText(currentQuestion.getOption3());
            option4RB.setText(currentQuestion.getOption4());

            questionCounter++;
            questionCountTV.setText("Question: " + questionCounter + " / " + questionTotalCount);
            answered = false;
            confirmBtn.setText("CONFIRM");
        }else{
            finishQuiz();
        }

    }

    private void finishQuiz(){
        finish();
    }
}
