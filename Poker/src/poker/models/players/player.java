package poker.models.players;

import javafx.animation.Timeline;
import poker.models.cards.Card;
import poker.models.cards.Hand;
import poker.models.enums.GameStage;

import java.util.List;

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

    public void setActive(){
        isFold = false;
    }

    public void makeMove(int bet, GameStage stage, List<Card> boardCards, int activePlayers){
        System.out.println("Players turn");
    }

    public int getMoney() {
        return this.money;
    }

    public void placeBet(int betSize) {
        money -= betSize;
        bet += betSize;
    }

    public void addMoney(int size){
        money += size;
    }

    public int getBet() {
        return this.bet;
    }
}
