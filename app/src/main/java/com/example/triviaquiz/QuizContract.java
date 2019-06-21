package com.example.triviaquiz;

import android.provider.BaseColumns;

//container for different constants used by sqLite
// final class ensures that no objects can be created
public final class QuizContract {
    //good practice to create an inner class for each different table
    // implementing BaseColumns interface to be able to assign an id to each column
    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NUMBER = "answer_number";
    }

    private QuizContract() {
    }
}
