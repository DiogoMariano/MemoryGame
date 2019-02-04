package com.example.memorygame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.example.memorygame.R;

public class gameActivity extends AppCompatActivity {
    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    private Context context;
    private Drawable backImage;
    private int [] [] cards;
    private List<Drawable> images;
    private com.example.memorygame.Card firstCard;
    private com.example.memorygame.Card secondCard;
    private ButtonListener buttonListener;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;


    private static Object lock = new Object();

    int turns;
    int number_of_cards;
    String type;
    String play;
    String time;
    int number_of_plays=0;
    private TableLayout mainTable;
    private UpdateCardsHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        handler = new UpdateCardsHandler();
        loadImages();

        chronometer = findViewById(R.id.cronos);

        backImage =  getResources().getDrawable(R.drawable.icon);


        buttonListener = new ButtonListener();

        mainTable = (TableLayout)findViewById(R.id.TableLayout03);

        context  = mainTable.getContext();

        Spinner s = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        s.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(android.widget.AdapterView<?> arg0, View arg1, int pos, long arg3){

                ((Spinner) findViewById(R.id.Spinner01)).setSelection(0);

                int x,y;

                switch (pos) {
                    case 1:
                        x=4;y=4;
                        break;
                    case 2:
                        x=4;y=5;
                        break;
                    case 3:
                        x=4;y=6;
                        break;
                    case 4:
                        x=5;y=6;
                        break;
                    case 5:
                        x=6;y=6;
                        break;
                    case 6:
                        x=5;y=5;
                        break;
                    default:
                        return;
                }
                type = "Type: " + Integer.toString(x) +"X"+ Integer.toString(y);
                number_of_plays = GameHistory.myPlays.size() - 1 ;
                play = "Play_"+Integer.toString(number_of_plays);

                newGame(x,y);

            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub (meter aqui um toast a dizer que é impossivel

            }

        });
    }

    private void newGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;

        cards = new int [COL_COUNT] [ROW_COUNT];


        //mainTable.removeView(findViewById(R.id.TableRow01));
        //mainTable.removeView(findViewById(R.id.TableRow02));

        TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
        //tr.removeAllViews();

        mainTable = new TableLayout(context);
        tr.addView(mainTable);

        for (int y = 0; y < ROW_COUNT; y++) {
            mainTable.addView(createRow(y));
        }

        firstCard=null;
        loadCards();

        number_of_cards=(r*c);
        ((TextView)findViewById(R.id.tv2)).setText("Number of cards: "+number_of_cards);
        turns=0;
        ((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
        running=true;
    }

    private void loadImages() {
        images = new ArrayList<Drawable>();

        images.add(getResources().getDrawable(R.drawable.card1, null));
        images.add(getResources().getDrawable(R.drawable.card2, null));
        images.add(getResources().getDrawable(R.drawable.card3, null));
        images.add(getResources().getDrawable(R.drawable.card4, null));
        images.add(getResources().getDrawable(R.drawable.card5, null));
        images.add(getResources().getDrawable(R.drawable.card6, null));
        images.add(getResources().getDrawable(R.drawable.card7, null));
        images.add(getResources().getDrawable(R.drawable.card8,null));
        images.add(getResources().getDrawable(R.drawable.card9, null));
        images.add(getResources().getDrawable(R.drawable.card10, null));
        images.add(getResources().getDrawable(R.drawable.card11, null));
        images.add(getResources().getDrawable(R.drawable.card12, null));
        images.add(getResources().getDrawable(R.drawable.card13, null));
        images.add(getResources().getDrawable(R.drawable.card14,null));
        images.add(getResources().getDrawable(R.drawable.card15, null));
        images.add(getResources().getDrawable(R.drawable.card16, null));
        images.add(getResources().getDrawable(R.drawable.card17, null));
        images.add(getResources().getDrawable(R.drawable.card18,null));
        images.add(getResources().getDrawable(R.drawable.card19,null));
        images.add(getResources().getDrawable(R.drawable.card20, null));
        images.add(getResources().getDrawable(R.drawable.card21, null));

    }

    private void loadCards(){
        try{
            int size = ROW_COUNT*COL_COUNT;

            Log.i("loadCards()","size=" + size);

            ArrayList<Integer> list = new ArrayList<Integer>();

            for(int i=0;i<size;i++){
                list.add(new Integer(i));
            }


            Random r = new Random();

            for(int i=size-1;i>=0;i--){
                int t=0;

                if(i>0){
                    t = r.nextInt(i);
                }

                t=list.remove(t).intValue();
                cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);

                Log.i("loadCards()", "card["+(i%COL_COUNT)+
                        "]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
            }
        }
        catch (Exception e) {
            Log.e("loadCards()", e+"");
        }

    }

    private TableRow createRow(int y){
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x,y));
        }
        return row;
    }

    private View createImageButton(int x, int y){
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100*x+y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            synchronized (lock) {
                if(firstCard!=null && secondCard != null){
                    return;
                }
                int id = v.getId();
                int x = id/100;
                int y = id%100;
                if(number_of_cards==1)
                {
                    endGame();
                }
                else
                    turnCard((Button)v,x,y);
            }

        }

        private void turnCard(Button button,int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if(firstCard==null){
                firstCard = new Card(button,x,y);
            }
            else{

                if(firstCard.x == x && firstCard.y == y){
                    return; //the user pressed the same card
                }

                secondCard = new Card(button,x,y);

                turns++;
                ((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);


                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        try{
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        catch (Exception e) {
                            Log.e("E1", e.getMessage());
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 1000);
            }


        }

    }

    class UpdateCardsHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }
        public void checkCards(){
            if(cards[secondCard.x][secondCard.y] == cards[firstCard.x][firstCard.y]){
                firstCard.button.setVisibility(View.INVISIBLE);
                secondCard.button.setVisibility(View.INVISIBLE);
                number_of_cards-=2;
                ((TextView)findViewById(R.id.tv2)).setText("Missing: "+number_of_cards);
                if(number_of_cards==0)
                {
                    endGame();
                }

            }
            else {
                secondCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
            }

            firstCard=null;
            secondCard=null;
        }
    }

    public void endGame(){
        pauseOffset = SystemClock.elapsedRealtime()- chronometer.getBase();
        chronometer.setBase(SystemClock.elapsedRealtime());
        running=false;
        time =  "Time: " + String.format("%d : %d", TimeUnit.MILLISECONDS.toMinutes(pauseOffset), TimeUnit.MILLISECONDS.toSeconds(pauseOffset) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(pauseOffset)));

        play tmp = new play(play, "Tries: " + Integer.toString(turns),time , type);
        GameHistory.myPlays.add(tmp);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }




}