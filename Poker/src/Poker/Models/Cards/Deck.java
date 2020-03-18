package Poker.Models.Cards;

import Poker.Models.Enums.CardSuit;
import Poker.Models.Enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<Card>();
        this.FillDeck();
    }

    public void Shuffle(){
        Collections.shuffle(cards);
    }

    private void FillDeck(){
        for(CardSuit suit: CardSuit.values()){
            for(CardValue value: CardValue.values()){
                this.AddCard(suit, value);
            }
        }
    }

    private void AddCard(CardSuit suit, CardValue value){
        Card card = new Card(suit, value);
        cards.add(card);
    }
}
