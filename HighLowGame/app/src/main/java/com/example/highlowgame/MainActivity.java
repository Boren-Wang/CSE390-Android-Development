package com.example.highlowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static int num = (int) (100*Math.random());
    static int limit = 10;
    static int count = 0;
    static boolean finished = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                TextView feedback = (TextView) findViewById(R.id.feedback);
                TextView feedback2 = (TextView) findViewById(R.id.feedback2);
                if(finished) {
                    editText.setText("");
                    feedback.setText("");
                    feedback2.setText("");
                    button.setText("Guess");
                    num = (int) (100*Math.random());
                    count = 0;
                    finished = false;
                    return;
                }

                int guess;
                try {
                    guess = Integer.parseInt(editText.getText().toString());
                } catch(Exception e) {
                    feedback.setText("Invalid Input!");
                    feedback.setTextColor(Color.parseColor("#FF0000")); //red
                    return;
                }
                count++;
                if(guess<num) {
                    feedback.setText("You guess is too low.");
                    feedback.setTextColor(Color.parseColor("#FF0000")); // red
                } else if(guess>num) {
                    feedback.setText("You guess is too high.");
                    feedback.setTextColor(Color.parseColor("#FF0000")); //red
                } else {
                    feedback.setText("You guess is correct!");
                    feedback.setTextColor(Color.parseColor("#008000")); // green
                    feedback2.setText("");
                    button.setText("Play Again");
                    finished = true;
                    return;
                }
                feedback2.setText("You have tried "+count+" times.\nThe number of remaining guesses is "+(limit-count)+".");
                if(count==limit) {
                    button.setText("Play Again");
                    finished = true;
                }
            }
        });
    }
}


