package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initHome();
        initSettings();
        initSortByClick();
        initSortOrderClick();
    }

    private void initHome() {
        ImageView home = (ImageView) findViewById(R.id.buttonHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void initSettings() {
        String sortBy = getSharedPreferences("ShoppingListPreferences",
                Context.MODE_PRIVATE).getString("sortfield","name");
        String sortOrder = getSharedPreferences("ShoppingListPreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbName = findViewById(R.id.rbName);
        RadioButton rbPrice = findViewById(R.id.rbPrice);
        RadioButton rbStatus = findViewById(R.id.rbStatus);
        if (sortBy.equalsIgnoreCase("name")) {
            rbName.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("price")) {
            rbPrice.setChecked(true);
        }
        else {
            rbStatus.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.rbAscending);
        RadioButton rbDescending = findViewById(R.id.rbDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbName = findViewById(R.id.rbName);
                RadioButton rbPrice = findViewById(R.id.rbPrice);
                if (rbName.isChecked()) {
                    getSharedPreferences("ShoppingListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "name").apply();
                }
                else if (rbPrice.isChecked()) {
                    getSharedPreferences("ShoppingListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "price").apply();
                }
                else {
                    getSharedPreferences("ShoppingListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "purchased").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = findViewById(R.id.rbAscending);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("ShoppingListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                }
                else {
                    getSharedPreferences("ShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }
}
