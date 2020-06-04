package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText first = (EditText) findViewById(R.id.firstEditText);
        final EditText second = (EditText) findViewById(R.id.secondEditText);
        final Button plusButton = (Button) findViewById(R.id.plusButton);
        final Button minusButton = (Button) findViewById(R.id.minusButton);
        final Button multiplyButton = (Button) findViewById(R.id.multiplyButton);
        final Button divideButton = (Button) findViewById(R.id.divideButton);
        final TextView answer = (TextView) findViewById(R.id.answer);

        // set listener for each button
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double num1 = Double.parseDouble(first.getText().toString());
                    double num2 = Double.parseDouble(second.getText().toString());
                    String ans = String.format("%.2f", num1+num2);
                    answer.setText( ans );
                    answer.setTextColor(Color.parseColor("#3F51B5"));
                } catch(Exception e) {
                    answer.setText("Invalid Input!");
                    answer.setTextColor(Color.RED);
                }
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double num1 = Double.parseDouble(first.getText().toString());
                    double num2 = Double.parseDouble(second.getText().toString());
                    String ans = String.format("%.2f", num1-num2);
                    answer.setText( ans );
                    answer.setTextColor(Color.parseColor("#3F51B5"));
                } catch(Exception e) {
                    answer.setText("Invalid Input!");
                    answer.setTextColor(Color.RED);
                }
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double num1 = Double.parseDouble(first.getText().toString());
                    double num2 = Double.parseDouble(second.getText().toString());
                    String ans = String.format("%.2f", num1*num2);
                    if(ans.equals("-0.00")) {
                        ans = "0.00";
                    }
                    answer.setText( ans );
                    answer.setTextColor(Color.parseColor("#3F51B5"));
                } catch(Exception e) {
                    answer.setText("Invalid Input!");
                    answer.setTextColor(Color.RED);
                }
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double num1 = Double.parseDouble(first.getText().toString());
                    double num2 = Double.parseDouble(second.getText().toString());
                    String ans = String.format("%.2f", num1/num2);
                    if(num2==0) {
                        answer.setText( "Invalid Input: Division by Zero" );
                        answer.setTextColor(Color.RED);
                        return;
                    }
                    answer.setText( ans );
                    answer.setTextColor(Color.parseColor("#3F51B5"));
                } catch(Exception e) {
                    answer.setText("Invalid Input!");
                    answer.setTextColor(Color.RED);
                }
            }
        });
    }
}
