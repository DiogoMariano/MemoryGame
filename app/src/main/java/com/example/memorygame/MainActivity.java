package com.example.memorygame;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.example.memorygame.R;

public class MainActivity extends AppCompatActivity {

    private gameActivity.ButtonListener buttonListener;

    private Button button1;
    private Button button2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //restorePlays();

        setContentView(R.layout.activity_main);



       button1 = (Button) findViewById(R.id.Play);
       button2 = (Button) findViewById(R.id.Records);

       button1.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               startGame();
           }
       });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goHistory();
            }
        });
    }

    public void startGame(){
        Intent intent = new Intent(this, gameActivity.class);
        startActivity(intent);
    }

    public void goHistory(){
        Intent intent = new Intent(this, GameHistory.class);
        startActivity(intent);
    }

  /* private boolean savePlays(){
        SharedPreferences plays = getSharedPreferences(GameHistory.PLAY_FILE, MODE_PRIVATE);

        SharedPreferences.Editor editor = plays.edit();

        editor.clear();

        editor.putInt(GameHistory.NUM_PLAYS, GameHistory.myPlays.size());

        for(Integer i = 0; i<GameHistory.myPlays.size(); i++){
            editor.putString(GameHistory.PLAY + i.toString(), GameHistory.myPlays.get(i).title);
            editor.putString(GameHistory.TRIES + i.toString(), GameHistory.myPlays.get(i).tries);
            editor.putString(GameHistory.TIME + i.toString(), GameHistory.myPlays.get(i).time);
            editor.putString(GameHistory.TYPE + i.toString(), GameHistory.myPlays.get(i).type);
        }

        return editor.commit();
    }*/

   /* private void restorePlays(){
        SharedPreferences plays = getSharedPreferences(GameHistory.PLAY_FILE, MODE_PRIVATE);
        int numOfTasks = plays.getInt("NumOfTasks", 0);
        if(numOfTasks!=0){
            GameHistory.myPlays.clear();
            for(Integer i=0; i<numOfTasks; i++){
                String title = plays.getString(GameHistory.PLAY + i.toString(), "0");
                String tries = plays.getString(GameHistory.TRIES + i.toString(),"0");
                String time = plays.getString(GameHistory.TIME + i.toString(),"0");
                String type = plays.getString(GameHistory.TYPE + i.toString(),"0");
                play tmp = new play(title,tries,time,type);

                GameHistory.myPlays.add(tmp);
            }
        }
    }
/*
    private void savePlaysToFile(){
        String filename = "myPlays.txt";
        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputStream.getFD()));
            String delim = ";";

            for(Integer i=0; i<GameHistory.myPlays.size();i++){
                play tmp = GameHistory.myPlays.get(i);
                String line = tmp.title + delim + tmp.tries + delim + tmp.time + delim + tmp.type;
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

    /*@Override
    public void onResume(){
        super.onResume();
        /*PlayListFragment taskFr = (PlayListFragment)getSupportFragmentManager().findFragmentById(R.id.playFragment);
        ArrayAdapter<play> taskAdapter = (ArrayAdapter<play>) taskFr.getListAdapter();
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(!savePlays()){
            Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_LONG).show();
        }
        savePlaysToFile();
    }*/


}