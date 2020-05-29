package poker.models.players;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.util.Duration;
import poker.models.cards.Hand;
import poker.models.enums.GameStage;

import java.io.IOException;
import java.util.TimerTask;

public class Player {
    private Hand hand;
    private int money;
    private int bet;
    private boolean isEndTurn = false;
    private boolean isFold = false;
    private boolean isDealer;
    private int[] time = {30};
    private Timeline timeline;

    public Player(){
        money = 2500;
        isDealer = false;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand(){
        return hand;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void setEndTurn(Boolean state){
        isEndTurn = state;
    }

    public boolean isEndTurn() {
        return isEndTurn;
    }

    public void setFold() {
        isFold = true;
        bet = 0;
    }

    public void makeMove(int betSize, int bigBlind, GameStage stage) {
        System.out.println("Players turn");
        startTimer();
    }

    private void startTimer(){

    }

    private void stopTimer(){

        isEndTurn = false;
    }

    public int getMoney() {
        return this.money;
    }

    public void placeBet(int betSize) {
        money -= betSize;
        bet += betSize;
    }

    public int getBet() {
        return this.bet;
    }
}
