package com.example.triqui_v1_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    // Represents the internal state of the game
    private TriquiGame mGame;
    // Buttons making up the board
    private Button mBoardButtons[];
    private Button reiniciar;
    // Various text displayed
    private TextView mInfoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons = new Button[TriquiGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button);
        mBoardButtons[1] = (Button) findViewById(R.id.button2);
        mBoardButtons[2] = (Button) findViewById(R.id.button3);
        mBoardButtons[3] = (Button) findViewById(R.id.button4);
        mBoardButtons[4] = (Button) findViewById(R.id.button5);
        mBoardButtons[5] = (Button) findViewById(R.id.button6);
        mBoardButtons[6] = (Button) findViewById(R.id.button7);
        mBoardButtons[7] = (Button) findViewById(R.id.button8);
        mBoardButtons[8] = (Button) findViewById(R.id.button9);
        ;
        mInfoTextView = (TextView) findViewById(R.id.information);
        reiniciar = (Button) findViewById(R.id.reset);
        mGame = new TriquiGame();
        startNewGame();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.quit_question)
                .setCancelable(false)
                .setPositiveButton("SIUU", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Nell", null);
        dialog = builder.create();
        return dialog;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_game:
                NewGame();
                System.out.println("Boton new presionado");
                return true;
            case R.id.easy:
                System.out.println("Boton easy presionado");
                NewGame();
                mGame.setDifficultyLevel(TriquiGame.DifficultyLevel.Easy);
                return true;
            case R.id.hard:
                NewGame();
                System.out.println("Boton hard presionado");
                mGame.setDifficultyLevel(TriquiGame.DifficultyLevel.Harder);
                return true;
            case R.id.hardmas:
                NewGame();
                System.out.println("Boton extreme presionado");
                mGame.setDifficultyLevel(TriquiGame.DifficultyLevel.Expert);
                return true;
            case R.id.Exit:
                showDialog(1);
                System.out.println("Boton exit presionado");
                return true;
            default:
                System.out.println("nada");
        }
        return false;
    }


    private void startNewGame() {
        //mGame.clearBoard();
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        reiniciar.setEnabled(true);
        reiniciar.setOnClickListener(new ButtonClickListener(9));
        mInfoTextView.setText("You go first.");
    }

    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }
        public void onClick(View view) {
            if(location!=9) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TriquiGame.HUMAN_PLAYER, location);
                    // If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText("It's Android's turn.");
                        System.out.println("quejta pajando");
                        int move = mGame.getComputerMove();
                        System.out.println("Compu mueve a:" + move);
                        setMove(TriquiGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0)
                        mInfoTextView.setText("It's your turn.");
                    else if (winner == 1)
                        mInfoTextView.setText("It's a tie!");
                    else if (winner == 2)
                        mInfoTextView.setText("You won!");
                    else
                        mInfoTextView.setText("Android won!");
                }
            }
            else{
                NewGame();
                mInfoTextView.setText("You have a new chance. It's your turn!");
            }
        }
    }
    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TriquiGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }
    private void NewGame() {
        //mGame.clearBoard();
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
        }
        reiniciar.setEnabled(true);
        mInfoTextView.setText("You go first.");
        mGame.clearBoard();
    }
}