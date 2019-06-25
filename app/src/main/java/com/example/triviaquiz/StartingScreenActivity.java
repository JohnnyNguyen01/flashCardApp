package com.example.triviaquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;

public class StartingScreenActivity extends AppCompatActivity {
    private TextView titleTv;
    private TextView highScoreTv;
    private Button startQuizBtn;
    private Button quitBtn;
    private int highScore;
    private static final int REQUEST_CODE = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen_activity);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initialize();
    }

    /*
    *  Starts a new quiz Quiz_Activity activity
    * */
    private void startQuiz(){
       Intent intent = new Intent(getApplicationContext(),Quiz_Activity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(Quiz_Activity.EXTRA_SCORE,0);
                if(score > highScore){
                    updateHighScore(score);
                }
            }
        }
    }


    private void updateHighScore(int score){
        highScore = score;
        highScoreTv.setText("HIGH SCORE: " + highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();
    }

    private void loadHighSCore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE, highScore);
        highScoreTv.setText("HIGH SCORE: " + highScore);
    }

    /*
    *  Initializes all fields
    * */
    private void initialize(){
        titleTv = findViewById(R.id.titleTV);
        highScoreTv = findViewById(R.id.highScoreTV);
        loadHighSCore();
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

    //Create (infalte) appbar menu with our main_menu.xml
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    //program appbar menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addQuestion:
                startActivity(new Intent(getApplicationContext(),AddQuestion.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
