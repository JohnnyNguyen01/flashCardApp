package com.example.triviaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddQuestion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView headingTV;
    private TextView addQuestionTV;
    private EditText addQuestionET;
    private TextView option1TV;
    private EditText option1ET;
    private TextView option2TV;
    private EditText option2ET;
    private TextView option3TV;
    private EditText option3ET;
    private TextView option4TV;
    private TextView correctAnswerTV;
    private Spinner correctAnswerSpinner;
    private Button addQuestionBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        initialize();
        handleAddQuestionBtn();
    }

    private void handleAddQuestionBtn(){
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTxtIsEmpty(addQuestionET) || editTxtIsEmpty(option1ET) ||
                        editTxtIsEmpty(option2ET)|| editTxtIsEmpty(option3ET)){
                    Toast.makeText(AddQuestion.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    String question = addQuestionET.getText().toString();
                    String option1 = option1ET.getText().toString();
                    String option2 = option2ET.getText().toString();
                    String option3 = option3ET.getText().toString();
                    int answer = Integer.parseInt(correctAnswerSpinner.getSelectedItem().toString().replace("Option ",""));
                    QuizDBHelper.getInstance(getApplicationContext()).addQuestion(new Question(question,option1,option2,option3,null,answer));
                    Toast.makeText(AddQuestion.this, "Question Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean editTxtIsEmpty(EditText editText){
        return editText.getText().toString().isEmpty();
    }

    private void initialize(){
        headingTV = findViewById(R.id.placeHolderTV);
        addQuestionTV = findViewById(R.id.questionTV);
        addQuestionET = findViewById(R.id.addQuestionET);
        option1TV = findViewById(R.id.option1TV);
        option1ET = findViewById(R.id.option1ET);
        option2TV = findViewById(R.id.option2TV);
        option2ET = findViewById(R.id.option2ET);
        option3TV = findViewById(R.id.option3TV);
        option3ET = findViewById(R.id.option3ET);
        correctAnswerTV = findViewById(R.id.correctAnswerTV);
        correctAnswerSpinner = findViewById(R.id.correctAnswerSpinner);
        addQuestionBtn = findViewById(R.id.addQuestionBtn);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.addQuestionActivitySpinnerItems, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correctAnswerSpinner.setAdapter(spinnerAdapter);
        correctAnswerSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
