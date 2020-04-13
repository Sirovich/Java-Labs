package Poker.Models.Players;

import Poker.Models.Cards.Hand;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Player{
    private Hand hand;
    private int money;
    private int bet;
    private boolean isFold = false;
    private boolean isDealer;

    public Player(){
        money = 2500;
        this.hand = new Hand();
        isDealer = false;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void setFold() {
        isFold = true;
    }

    public void makeMove() {

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
