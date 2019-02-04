package com.example.memorygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameHistory extends AppCompatActivity {

    public static final String PLAY_FILE = "com.example.memorygame.PlayFile";
    public static final String NUM_PLAYS = "NumOfPlays";
    public static final String PLAY = "Play_";
    public static final String TRIES = "Tries_";
    public static final String TIME = "Time_";
    public static final String TYPE = "Type_";

    static public ArrayList<play> myPlays;
    public static final String playExtra = "Play";

    static {
        myPlays = new ArrayList<play>();
        myPlays.add(new play("Test", "1", "1", "test"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        restorePlays();
        final PlayListFragment playFr = (PlayListFragment) getSupportFragmentManager().findFragmentById(R.id.playFragment);
        final ArrayAdapter<play> playAdapter = (ArrayAdapter<play>) playFr.getListAdapter();

        playFr.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Play selected!", Toast.LENGTH_SHORT).show();
                startSecondActivity(parent, position);
            }
        });


    }

    private void startSecondActivity(AdapterView<?> parent, int position) {
        Intent intent = new Intent(this, PlayInfoActivity.class);
        play tmp = (play) parent.getItemAtPosition(position);

        intent.putExtra(playExtra, tmp);
        startActivity(intent);
        finish();
    }

    private boolean savePlays(){
        SharedPreferences tasks = getSharedPreferences(PLAY_FILE, MODE_PRIVATE);

        SharedPreferences.Editor editor = tasks.edit();

        editor.clear();

        editor.putInt(NUM_PLAYS, myPlays.size());

        for(Integer i = 0; i<myPlays.size(); i++){
            editor.putString(PLAY + i.toString(), myPlays.get(i).title);
            editor.putString(TRIES + i.toString(), myPlays.get(i).tries);
            editor.putString(TIME + i.toString(), myPlays.get(i).time);
            editor.putString(TYPE + i.toString(), myPlays.get(i).type);
        }

        return editor.commit();
    }

    private void restorePlays(){
        SharedPreferences plays = getSharedPreferences(PLAY_FILE, MODE_PRIVATE);
        int numOfTasks = plays.getInt("NumOfTasks", 0);
        if(numOfTasks!=0){
            myPlays.clear();
            for(Integer i=0; i<numOfTasks; i++){
                String title = plays.getString(PLAY + i.toString(), "0");
                String tries = plays.getString(TRIES + i.toString(),"0");
                String time = plays.getString(TIME + i.toString(),"0");
                String type = plays.getString(TYPE + i.toString(),"");
                play tmp = new play(title,tries,time,type);

                myPlays.add(tmp);
            }
        }
    }

    private void savePlaysToFile(){
        String filename = "myPlays.txt";
        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputStream.getFD()));
            String delim = ";";

            for(Integer i=0; i<myPlays.size();i++){
                play tmp = myPlays.get(i);
                String line = tmp.title + delim + tmp.tries + delim + tmp.time + delim + tmp.type;
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
       PlayListFragment taskFr = (PlayListFragment)getSupportFragmentManager().findFragmentById(R.id.playFragment);
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
    }

}