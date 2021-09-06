package com.tanxe.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.tictactoe.R;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    TextView headerText;

    private TextView idPlayer1Score,idPlayer2Score;

    private int idPlayer1ScoreCount,idPlayer2ScoreCount,count;
    int Player1 = 0;
    int Player2 = 1;

    int activePlayer = Player1;

    int[] filledPos = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-5};

    int[][] winningPos = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    boolean isGameActive = true;
    private Calendar FirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "das");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Dfasf");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
//
//        mFirebaseAnalytics.setUserProperty("favor","Das");
//
//        mFirebaseAnalytics.setCurrentScreen(this, "MainActivity",null);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }




        idPlayer1Score=(TextView)findViewById(R.id.idPlayer1Score);
        idPlayer2Score=(TextView)findViewById(R.id.idPlayer2Score);

        headerText = findViewById(R.id.idTVhead);
        headerText.setText("Player 1 turn");


        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        idPlayer1ScoreCount=0;
        idPlayer2ScoreCount=0;


    }

    @Override
    public void onClick(View v) {
        // logic for button press
        if(!isGameActive)
            return;

        Button clickedBtn = findViewById(v.getId());
        int clickedTag = Integer.parseInt(v.getTag().toString());

        if(filledPos[clickedTag]==-5)
            restartGame();
        if(filledPos[clickedTag] != -1)
        {
            return;
        }
        filledPos[clickedTag] = activePlayer;

        if(activePlayer == Player1)
        {
            clickedBtn.setText("O");
            clickedBtn.setBackground(getDrawable(android.R.color.holo_blue_bright));
            activePlayer = Player2;
            headerText.setText("Player 2 turn");
        }
        else
        {
            clickedBtn.setText("X");
            clickedBtn.setBackground(getDrawable(android.R.color.holo_orange_light));
            activePlayer = Player1;
            headerText.setText("Player 1 turn");
        }
        count++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(checkForWin()==1)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                result();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(count==9)
        {
            idPlayer1ScoreCount++;
            idPlayer2ScoreCount++;
            showDialog("Match tied");
            playAgain();
        }
        updateScore();
    }

    private void result() throws InterruptedException {
        count=0;

        for(int i =0 ;i<8;i++)
        {
            int val0  = winningPos[i][0];
            int val1  = winningPos[i][1];
            int val2  = winningPos[i][2];

            if(filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2])
            {
                if(filledPos[val0] != -1)
                {
                    //  winner
                    isGameActive = false;

                    if(filledPos[val0] == Player1)
                    {
                        idPlayer1ScoreCount++;
                        Thread.sleep(100);
                        showDialog("Player 1 is winner");
                        playAgain();
                    }
                    else {
                        idPlayer2ScoreCount++;
                        Thread.sleep(100);
                        showDialog("Player 2 is winner");
                        playAgain();
                    }
                }
            }
        }
    }
    private int checkForWin()
    {

        int flag=0;

        for(int i =0 ;i<8;i++)
        {
            int val0  = winningPos[i][0];
            int val1  = winningPos[i][1];
            int val2  = winningPos[i][2];

            if(filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]){
                if(filledPos[val0] != -1)
                {
//                    winner
                    isGameActive = false;

                    if(filledPos[val0] == Player1)
                    {
                        flag=1;
                    }
                    else {
                        flag=1;
                    }
                }
            }
        }
        return flag;
    }

    public void updateScore()
    {
        idPlayer1Score.setText(Integer.toString(idPlayer1ScoreCount));
        idPlayer2Score.setText(Integer.toString(idPlayer2ScoreCount));
    }

    public void playAgain()
    {
        activePlayer=Player1;
        headerText.setText("Player 1 turn");
        filledPos = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-5};

        count=0;

        btn0.setText("");
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");


        btn1.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn0.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn2.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn3.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn4.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn5.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn6.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn7.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn8.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn9.setBackground(getDrawable(android.R.color.holo_blue_dark));
        btn9.setText("RESET");

        isGameActive = true;

    }

    private void showDialog(String winnerText)
    {

        new AlertDialog.Builder(this)

                .setTitle(winnerText)
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        playAgain();
                    }
                })
                .show();
    }


    private void restartGame(){
        activePlayer = Player1;
        headerText.setText("Player 1 turn");
        filledPos = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-5};
        btn0.setText("");
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");


        btn0.setBackground(getDrawable(android.R.color.darker_gray));
        btn1.setBackground(getDrawable(android.R.color.darker_gray));
        btn2.setBackground(getDrawable(android.R.color.darker_gray));
        btn3.setBackground(getDrawable(android.R.color.darker_gray));
        btn4.setBackground(getDrawable(android.R.color.darker_gray));
        btn5.setBackground(getDrawable(android.R.color.darker_gray));
        btn6.setBackground(getDrawable(android.R.color.darker_gray));
        btn7.setBackground(getDrawable(android.R.color.darker_gray));
        btn8.setBackground(getDrawable(android.R.color.darker_gray));
        btn9.setBackground(getDrawable(android.R.color.darker_gray));
        btn9.setText("RESET");

        idPlayer1ScoreCount=0;
        idPlayer2ScoreCount=0;
        isGameActive = true;
        updateScore();
    }

}