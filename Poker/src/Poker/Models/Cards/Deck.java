package Poker.Models.Cards;

import Poker.Models.Enums.CardSuit;
import Poker.Models.Enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<Card>();
        this.FillDeck();
    }

    public void Shuffle(){
        Collections.shuffle(cards);
    }

    public Hand getCardsHand(){
        Random random = new Random();

        int firstCardIndex = random.nextInt(cards.size());
        Card firstCard = this.cards.get(firstCardIndex);
        this.cards.remove(firstCardIndex);

        int secondCardIndex = random.nextInt(cards.size());
        Card secondCard = this.cards.get(secondCardIndex);
        cards.remove(secondCardIndex);

        return new Hand(firstCard, secondCard);
    }

    private void FillDeck(){
        this.cards.clear(); //SRP
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
