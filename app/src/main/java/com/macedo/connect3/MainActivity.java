package com.macedo.connect3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yello / 1 = red
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(2000);

            for(int[] winningPosition : winningPositions){
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2){

                    gameIsActive = false;

                    String winner = "Red";
                    if (gameState[winningPosition[0]] == 0){
                        winner = "Yellow";
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMesssage);
                    winnerMessage.setText(winner + " has won!");

                    LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    winnerLayout.setVisibility(View.VISIBLE);


                } else {
                    boolean gameIsOver = true;
                    // Checking draw
                    for(int counterState : gameState){
                        if(counterState == 2){
                            gameIsOver = false;
                        }
                    }

                    if(gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMesssage);
                        winnerMessage.setText("It's a draw!");

                        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        winnerLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    }

    public void playAgain(View view){
        // Hiding winner label
        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        winnerLayout.setVisibility(View.INVISIBLE);

        gameIsActive = true;

        // Resetting player
        activePlayer = 0;

        // Reseting all spaces
        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
