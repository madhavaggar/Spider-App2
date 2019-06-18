package com.example.game.spidertask2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    int score=0;
    DatabaseHandler db=new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView text = findViewById(R.id.textView);
        int i=0;
        Intent intent= getIntent();
        Bundle retrieve = intent.getExtras();
        score= Integer.parseInt(retrieve.getString("score"));
        if(score!=0) {
            db.addValue(new Time(score));
        }
        List<Time> timelist=db.getAll();
        for(Time time: timelist) {
            i=time.getTime();
            text.append("\nHours: " + i / 10000 + "" + "\tMinutes: " + (i / 100) % 100 + "" + "\tSeconds: " + i % 100 + "");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                finish();
            }
        }, 5000);
    }
}
