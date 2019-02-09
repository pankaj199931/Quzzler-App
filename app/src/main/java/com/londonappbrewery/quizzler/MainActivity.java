package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UProperty.MATH;

public class MainActivity extends Activity {

    // TODO: Declare constants here

    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView ;
    int mIndex;
    int question;
    int mScore;


    TextView mScoreTextView;
    ProgressBar mProgressBar;

    // TODO: Declare member variables here:


     //TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };
    final int PROGRESS_BAR_INCREMENT = (int ) Math.ceil(100/mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null)
        {
            mScore =savedInstanceState.getInt("Score");
            mIndex = savedInstanceState.getInt("IndexKey");
            updateQuestion();
        }
        else
        {
            mScore=0;
            mIndex=0;
        }
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView= (TextView) findViewById(R.id.score);
        mProgressBar= (ProgressBar) findViewById(R.id.progress_bar);

         question =mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(question);

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quizzler", "Button Pressed");
                Toast.makeText(getApplicationContext(), "True Pressed", Toast.LENGTH_SHORT).show();
                checkAnswer(true);
                updateQuestion();

            }
        };
        mTrueButton.setOnClickListener(myListener);

        mFalseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Toast myToast = Toast.makeText(getApplicationContext(),"false Pressed", Toast.LENGTH_SHORT);
                       myToast.show();
                        checkAnswer(false);
                        updateQuestion();

                    }
                }
        );

    }

    private void updateQuestion()
    {

        mIndex= (mIndex+1)%mQuestionBank.length;
        if(mIndex ==0)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);


            alert.setMessage("You Scored "+ mScore + " points !");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();


        }

        question = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(question);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score" + mScore+"/"+ mQuestionBank.length);
    }
    private void checkAnswer(boolean userSelection){
        boolean correctAnswer =  mQuestionBank[mIndex].ismAnswer();
        if(userSelection==correctAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore=mScore+1;
        }
        else{
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Score",mScore);
        outState.putInt("IndexKey", mIndex);
    }

}
