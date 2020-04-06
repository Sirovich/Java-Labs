package Poker.Models.Players;

import Poker.Models.Cards.Hand;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Player implements IPlayer {
    @FXML
    private HBox playerHand;
    private Hand hand;
    private int money;
    private boolean isDealer;

    public Player(){
        money = 2500;
        this.hand = new Hand();
        isDealer = false;
    }

    @Override
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public boolean isDealer() {
        return isDealer;
    }

    @Override
    public void makeMove() {

    }

    @Override
    public void placeBet(int betSize) {
        money -= betSize;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }

    public int getMoney() {
        return money;
    }


}
