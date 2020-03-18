package Poker.Models.Cards;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    public Hand(){
        this.cards = new ArrayList<Card>();
    }

    public void AddCard(Card card){
        if (card == null){
            throw new NullPointerException();
        }

        this.cards.add(card);
    }
}
