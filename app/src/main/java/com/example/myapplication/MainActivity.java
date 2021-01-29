package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int counter = 0;
    boolean isTraining = false;
    SharedPreferences prefs;
    String recordKey = "RECORD_KEY";
    String totalKey = "TOTAL_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getApplication().getSharedPreferences("PUSHUPAPP", MODE_PRIVATE);

        TextView totalCountView = findViewById(R.id.totalCount);
        TextView counterView = findViewById(R.id.counter);
        Button button = findViewById(R.id.button);

        totalCountView.setText("Total Pushups: " + getTotal());
        counterView.setText("Record\n" + getRecord());
        button.setText("Begin Training");

        counterView.setOnClickListener(v -> addToCount(button, counterView, totalCountView));
        button.setOnClickListener(v -> btnClicked(button, counterView, totalCountView));

    }

    public void btnClicked(Button button, TextView counterView, TextView totalCountView) {
        if (isTraining) {
            isTraining = false;
            if (counter > getRecord()) {
                saveRecord(counter);
            } else {
                Toast.makeText(this, "You can do better!", Toast.LENGTH_SHORT).show();
            }
            
            counterView.setText("Record\n" + getRecord());
            button.setText("Begin Training");
        } else {
            //möchte das Training beginnen
            isTraining = true;
            counter = 0;
            counterView.setText("Count\n" + counter);
            button.setText("Done");
            Toast.makeText(this, "Training began", Toast.LENGTH_SHORT).show();
        }
    }

    public void addToCount(Button button, TextView counterView, TextView totalCountView) {
        if (isTraining) {
            counter++;
            saveTotal(getTotal() + 1);
            counterView.setText("Count\n" + counter);
            totalCountView.setText("Total Pushups: " + getTotal());
        }
    }

    public int getRecord() {
        return prefs.getInt(recordKey, 0);
    }

    public void saveRecord(int value) {
        if (prefs.edit().putInt(recordKey, value).commit()) {
            Toast.makeText(this, "Good job!\nCount Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving Count", Toast.LENGTH_SHORT).show();
        }
    }

    public int getTotal() {
        return prefs.getInt(totalKey, 0);
    }

    public void saveTotal(int value) {
        prefs.edit().putInt(totalKey, value).apply();
    }
}