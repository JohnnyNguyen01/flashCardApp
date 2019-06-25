package com.example.triviaquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/*
 *
 * */
public class Quiz_Activity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private TextView questionTV;
    private TextView countDownTV;
    private TextView scoreTV;
    private TextView questionCountTV;
    private Button confirmBtn;
    private RadioButton option1RB;
    private RadioButton option2RB;
    private RadioButton option3RB;
    private RadioGroup rbGroup;
    private static final long COUNTDOWN_TIME_IN_MILLIS = 30000;

    private ColorStateList textColourDefaultRB; //save default RB text color to change later according to correct answer
    private ColorStateList textColourDefaultCD; //Save default RB countdown timer to change later according to correct

    private CountDownTimer countDownTimer;
    private long timeLeftInMillies;

    private ArrayList<Question> questionsList;

    private int questionCounter;
    private int questionTotalCount;
    private Question currentQuestion;
    private int score;
    private boolean answered;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initialize();
    }

    private void initialize() {
        questionTV = findViewById(R.id.questionTV);
        countDownTV = findViewById(R.id.countDownTV);
        scoreTV = findViewById(R.id.scoreTV);
        questionCountTV = findViewById(R.id.questionCountTV);
        confirmBtn = findViewById(R.id.confirmBtn);
        option1RB = findViewById(R.id.option1);
        option2RB = findViewById(R.id.option2);
        option3RB = findViewById(R.id.option3);
        rbGroup = findViewById(R.id.radio_group);

        textColourDefaultRB = option1RB.getTextColors();
        textColourDefaultCD = countDownTV.getTextColors();

        QuizDBHelper dbHelper = new QuizDBHelper(getApplicationContext());
        questionsList = dbHelper.getAllQuestions();
        questionTotalCount = questionsList.size();
        Collections.shuffle(questionsList); // randomizes appearance of questions
        showNextQuestion();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (option1RB.isChecked() || option2RB.isChecked() || option3RB.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(Quiz_Activity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                    rbGroup.clearCheck();
                }
            }
        });
    }

    private void showNextQuestion() {
        option1RB.setTextColor(textColourDefaultRB);
        option2RB.setTextColor(textColourDefaultRB);
        option3RB.setTextColor(textColourDefaultRB);


        if (questionCounter < questionTotalCount) {
            currentQuestion = questionsList.get(questionCounter);

            questionTV.setText(currentQuestion.getQuestion());
            option1RB.setText(currentQuestion.getOption1());
            option2RB.setText(currentQuestion.getOption2());
            option3RB.setText(currentQuestion.getOption3());


            questionCounter++;
            questionCountTV.setText("Question: " + questionCounter + " / " + questionTotalCount);
            answered = false;
            confirmBtn.setText("CONFIRM");

    //        Log.d("DEBUG", "showNextQuestion: " + option3RB.getText());
            Log.d("DEBUG", "opt2: " + currentQuestion.getOption2());
            Log.d("DEBUG", "opt3: " + currentQuestion.getOption3());
            Log.d("DEBUG", "opt4: " + currentQuestion.getOption4());

            timeLeftInMillies = COUNTDOWN_TIME_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillies,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillies = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillies = 0;
                updateCountDownText();
                checkAnswer();                          //TODO: toast or page that shows timer up
            }
        }.start();
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeftInMillies / 1000) / 60;
        int seconds = (int) (timeLeftInMillies/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        countDownTV.setText(timeFormatted);

        if(timeLeftInMillies < 10000){
            countDownTV.setTextColor(Color.RED);
        } else{
            countDownTV.setTextColor(textColourDefaultCD);
        }
    }

    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel();
        RadioButton checkedRB = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNumber = rbGroup.indexOfChild(checkedRB) + 1; //bro remember elements start at 0
        if (answerNumber == currentQuestion.getAnswerNr()) {
            score++;
            scoreTV.setText("Score: " + score);
        }
        showSolution();
    }

    // output correct radio button as green
    private void showSolution() {
        option1RB.setTextColor(Color.RED);
        option2RB.setTextColor(Color.RED);
        option3RB.setTextColor(Color.RED);


        switch (currentQuestion.getAnswerNr()) {
            case 1:
                option1RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 1 is correct!");
                break;
            case 2:
                option2RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 2 is correct!");
                break;
            case 3:
                option1RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 3 is correct!");
                break;
            case 4:
                option2RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 4 is correct!");
                break;
        }

        if (questionCounter < questionTotalCount) {
            confirmBtn.setText("NEXT");
        } else {
            confirmBtn.setText("FINISH");
        }
    }

    private void finishQuiz() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    //prevent user from using back button to reset score value
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }
}
