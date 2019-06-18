package com.example.game.spidertask2;

import android.util.Log;
import java.util.Random;

public class GameEngine {

    private static final Random RANDOM = new Random();
    private char[] elts;
    private char currentPlayer;
    private boolean ended;


    public GameEngine(){
        elts=new char[9];
        newGame();
    }

    public boolean isEnded(){
        return ended;
    }

    public char play(int x,int y,int option) {
        int found = 0;
        int i;
        if (option == 1) {
            if (!ended && elts[ x + 3 * y ] == ' ') {
                elts[ x + 3 * y ] = currentPlayer;
                changePlayer();
            }
        }
        else{
            elts[x+ y*3]=' ';
            changePlayer();
        }
        return checkEnd();
    }

    public void changePlayer(){
        currentPlayer= (currentPlayer == 'X' ? 'O' : 'X');
    }

    public char elt(int x,int y){
        if((x+y*3)<elts.length && (x+y*3)>=0 && x>=0 && y>=0 && x<3 && y<3)
            return elts[x + y*3];
        else
            return '\0';
    }

    public void newGame(){
        for(int i=0; i<elts.length ; i++){
            elts[i]=' ';
        }
        currentPlayer = 'X';
        ended=false;
    }

    public char checkEnd() {
        for (int i = 0; i < 3; i++) {
            if (elt(i, 0) != ' ' &&
                    elt(i, 0) == elt(i, 1)  &&
                    elt(i, 1) == elt(i, 2)) {
                ended = true;
                return elt(i, 0);
            }

            if (elt(0, i) != ' ' &&
                    elt(0, i) == elt(1, i)  &&
                    elt(1, i) == elt(2, i)) {
                ended = true;
                return elt(0, i);
            }
        }

        if (elt(0, 0) != ' '  &&
                elt(0, 0) == elt(1, 1)  &&
                elt(1, 1) == elt(2, 2)) {
            ended = true;
            return elt(0, 0);
        }

        if (elt(2, 0) != ' '  &&
                elt(2, 0) == elt(1, 1)  &&
                elt(1, 1) == elt(0, 2)) {
            ended = true;
            return elt(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (elts[i] == ' ')
                return ' ';
        }

        return 'T';
    }

    public char computer() {
        if (!ended) {
            int position = -1;

            do {
                position = RANDOM.nextInt(9);
            } while (elts[position] != ' ');

            elts[position] = currentPlayer;
            changePlayer();
        }

        return checkEnd();
    }
}
