package Poker.Models.Cards;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand(){
        this.cards = new ArrayList<Card>();
    }

    public Hand(Card first, Card second) {
        this.cards = new ArrayList<>();
        cards.add(first);
        cards.add(second);
    }

    public void AddCard(Card card){
        if (card == null){
            throw new NullPointerException();
        }

        this.cards.add(card);
    }

    public void clearHand(){
        cards.clear();
    }
}
