package br.usp.each.typerace.server;

import java.util.ArrayList;
import java.util.UUID;

//classe representando o jogador
public class Player {
    private UUID playerUId;
    private int score;
    private int wrong;
    private ArrayList<String> words;

    public void addScore(){
        score++;
    }

    public void addWrong(){
        wrong++;
    }

    public UUID getPlayerUId() {
        return playerUId;
    }

    public int getScore() {
        return score;
    }

    public int getWrong() {
        return wrong;
    }

    public ArrayList<String> getWords() {
        return words;
    }
}
