package Poker.Models.Players;

import Poker.Models.Cards.Hand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;

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

    public void makeMove(int betSize, int bigBlind) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Poker/Main.fxml"));
        Parent root = loader.load();

        Button bet = (Button)loader.getNamespace().get("bet");
        Button fold = (Button)loader.getNamespace().get("fold");
        Button call = (Button)loader.getNamespace().get("call");
        Button allIn = (Button)loader.getNamespace().get("allIn");


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
