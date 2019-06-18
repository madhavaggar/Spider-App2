package com.example.game.spidertask2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private CanvasView boardView;
    private GameEngine gameEngine;
    SimpleDateFormat mdformat = new SimpleDateFormat("hhmmss");
    String strDatestart,strDateend;
    int bestTime=1000000;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (CanvasView) findViewById(R.id.board);
        gameEngine = new GameEngine();
        Date date=new Date();
        strDatestart = mdformat.format(date);
        boardView.setGameEngine(gameEngine);
        boardView.setMainactivity(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if(pref.contains("BestTime"))
            bestTime=pref.getInt("BestTime",-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }
        if(item.getItemId() == R.id.undo){
            boardView.undo(gameEngine);
        }
        if(item.getItemId() == R.id.highscore){
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("score",0+"");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameEnded(char c) {
        String msg = (c == 'T') ? "Game Ended. Tie" : "GameEnded. " + c + " win";
            Date date = new Date();
            int i = 0;
            strDateend = mdformat.format(date);
            int hr2 = Integer.parseInt(strDateend) / 10000;
            int min2 = (Integer.parseInt(strDateend) / 100) % 100;
            int sec2 = Integer.parseInt(strDateend) % 100;
            int hr1 = Integer.parseInt(strDatestart) / 10000;
            int min1 = (Integer.parseInt(strDatestart) / 100) % 100;
            int sec1 = Integer.parseInt(strDatestart) % 100;
            i = (hr2 - hr1) * 10000;
            if (hr2 - hr1 < 0) {
                i = i + (24) * 10000;
            }
            i = i + (min2 - min1) * 100;
            if (min2 - min1 < 0) {
                i = i + (60) * 100;
                i = i - 10000;
            }
            i = i + sec2 - sec1;
            if (sec2 - sec1 < 0) {
                i = i + 60;
                i = i - 100;
            }
            if (bestTime > i)
                bestTime = i;
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("score",i+"");
            startActivity(intent);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("BestTime", bestTime);
            editor.commit();
        new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        gameEngine.newGame();
        boardView.invalidate();
        Date date=new Date();
        strDatestart = mdformat.format(date);
        }
}
