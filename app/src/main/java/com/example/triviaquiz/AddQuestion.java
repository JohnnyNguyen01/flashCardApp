package com.example.triviaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddQuestion extends AppCompatActivity {
    private TextView addQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        addQuestion = findViewById(R.id.addQuestion);
    }
}
