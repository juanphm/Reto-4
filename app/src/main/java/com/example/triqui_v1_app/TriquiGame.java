package com.example.triqui_v1_app;
//package edu.harding.tictactoe;

import android.annotation.SuppressLint;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TriquiGame {
    private char mBoard[] = {'1','2','3','4','5','6','7','8','9'};
    public static final int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';

    private Random mRand;

    public TriquiGame() {

        // Seed the random number generator
        mRand = new Random();


    }
    /** Clear the board of all X's and O's by setting all spots to OPEN_SPOT. */
    public void clearBoard(){
        for (int i = 0; i <  BOARD_SIZE; i++) {
            mBoard[i]=OPEN_SPOT;
        }

    }

    public enum DifficultyLevel {Easy, Harder, Expert};
    // Current difficulty level
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
    }
    /** Set the given player at the given location on the game board.
     * The location must be available, or the board will not be changed.
     *
     * @param player - The HUMAN_PLAYER or COMPUTER_PLAYER
     * @param location - The location (0-8) to place the move
     */
    public void setMove(char player, int location){
        System.out.println("Location is " + location + " Player is:"+ player);
        mBoard[location] = player;
        displayBoard();
    }


    /** Return the best move for the computer to make. You must call setMove()
     * to actually make the computer move to that location.
     * @return The best move for the computer to make (0-8).
     */

    /**
     * Check for a winner and return a status value indicating who has won.
     * @return Return 0 if no winner or tie yet, 1 if it's a tie, 2 if X won,
     * or 3 if O won.
     */


    private void displayBoard()	{
        System.out.println();
        System.out.println(mBoard[0] + " | " + mBoard[1] + " | " + mBoard[2]);
        System.out.println("-----------");
        System.out.println(mBoard[3] + " | " + mBoard[4] + " | " + mBoard[5]);
        System.out.println("-----------");
        System.out.println(mBoard[6] + " | " + mBoard[7] + " | " + mBoard[8]);
        System.out.println();
    }

    // Check for a winner.  Return
    //  0 if no winner or tie yet
    //  1 if it's a tie
    //  2 if X won
    //  3 if O won
    public int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6]== COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }


    @SuppressLint("SuspiciousIndentation")
    public int getComputerMove()
    {
        int move=-1;
        if (mDifficultyLevel == DifficultyLevel.Easy)
            move = getRandomMove();
        else if (mDifficultyLevel == DifficultyLevel.Harder) {
            move = getWinningMove();
            if (move == -1)
                move = getRandomMove();
        }
        else if (mDifficultyLevel == DifficultyLevel.Expert) {
            move = getWinningMove();
            if (move == -1)
                move = getBlockingMove();
            if (move == -1)
                move = getRandomMove();
        }
        mBoard[move] = COMPUTER_PLAYER;
        return move;
    }

    public int getRandomMove(){
        int move=-1;
        if(mBoard[4] != HUMAN_PLAYER && mBoard[4] != COMPUTER_PLAYER)
            move=4;
        else {
            do
            {
                move = mRand.nextInt(BOARD_SIZE);
            } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
        }

        System.out.println("Computer is moving to move: " + (move + 1));
        return move;
    }
    public int getBlockingMove(){
        int move=-1;
        for (int i = 0; i <= 6; i += 3) {
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER) {
                    return i + 2;
                } else if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER) {
                    return i + 1;
                } else if (mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER)
                    return i;
            }
        }

        for (int i = 0; i < 2; i++) {
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER){
                    return i+6;
                }else if(mBoard[i] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER){
                    return i+3;
                }else if(mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER)
                    return i;
            }
        }
        System.out.println("Computer is NOT moving to block: " + move);
        return move;
    }
    public int getWinningMove(){
        // Check horizontal wins
        for (int i = 0; i <= 2; i +=3)	{
           if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER){
               if(mBoard[i+1] == COMPUTER_PLAYER && mBoard[i+2] == COMPUTER_PLAYER){
                   return i;
               }
           }
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER){
                if(mBoard[i+3] == COMPUTER_PLAYER && mBoard[i+6] == COMPUTER_PLAYER){
                    return i;
                }
            }
        }

        // Check for diagonal wins
        if(mBoard[0] != HUMAN_PLAYER && mBoard[0] != COMPUTER_PLAYER){
            if(mBoard[2] == COMPUTER_PLAYER || mBoard[4] == COMPUTER_PLAYER
                    || mBoard[6] == COMPUTER_PLAYER|| mBoard[8] == COMPUTER_PLAYER){
                return 0;
            }
        }
        else if(mBoard[2] != HUMAN_PLAYER && mBoard[2] != COMPUTER_PLAYER){
            if(mBoard[0] == COMPUTER_PLAYER || mBoard[4] == COMPUTER_PLAYER
                    || mBoard[6] == COMPUTER_PLAYER|| mBoard[8] == COMPUTER_PLAYER){
                return 2;
            }
        }
        else if(mBoard[4] != HUMAN_PLAYER && mBoard[4] != COMPUTER_PLAYER){
            if(mBoard[0] == COMPUTER_PLAYER || mBoard[2] == COMPUTER_PLAYER
                    || mBoard[6] == COMPUTER_PLAYER|| mBoard[8] == COMPUTER_PLAYER){
                return 4;
            }
        }
        else if(mBoard[6] != HUMAN_PLAYER && mBoard[6] != COMPUTER_PLAYER){
            if(mBoard[0] == COMPUTER_PLAYER || mBoard[2] == COMPUTER_PLAYER
                    || mBoard[4] == COMPUTER_PLAYER|| mBoard[8] == COMPUTER_PLAYER){
                return 6;
            }
        }
        else if(mBoard[8] != HUMAN_PLAYER && mBoard[8] != COMPUTER_PLAYER){
            if(mBoard[0] == COMPUTER_PLAYER || mBoard[2] == COMPUTER_PLAYER
                    || mBoard[4] == COMPUTER_PLAYER|| mBoard[6] == COMPUTER_PLAYER){
                return 8;
            }
        }
        return -1;
    }


}
