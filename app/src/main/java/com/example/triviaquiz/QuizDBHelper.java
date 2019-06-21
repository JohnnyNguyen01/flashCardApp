package com.example.triviaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.triviaquiz.QuizContract;

/*
*  TODO: https://www.youtube.com/watch?v=pEDVdSUuWXE <----- next video
* */

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    //super constructor auto generated
    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        //JAVA's shit way of generating an sql table
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, "+
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, "+
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, "+
                QuizContract.QuestionsTable.COLUMN_OPTION4 + " TEXT, "+
                QuizContract.QuestionsTable.COLUMN_ANSWER_NUMBER + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    //only call onUpgrade if app is in production. s
    /* STEPS:
    * define changes in SQL_CREATE_QUESTIONS_TABLE
    * call db.execSQL("DROP TABLE IF EXISTS" + QuestionsTable.TABLE_NAME
    * call onCreate(db)
    * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*Create questions initially*/
    private void fillQuestionsTable(){
        //TODO: get rid of dummy data
        Question q1 = new Question("A is correct", "A","B","C","D",1);
        Question q2 = new Question("B is correct", "A","B","C","D",1);
        Question q3 = new Question("C is correct", "A","B","C","D",1);
        Question q4 = new Question("D is correct", "A","B","C","D",1);

    }

    //add new questions
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        //param 1 = what table you want to enter valeus into, param 2 = question object field
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NUMBER, question.getAnswerNr());

        db.insert(QuizContract.QuestionsTable.TABLE_NAME,null,cv);
    }
}
