package poker.models.cards;

import poker.models.enums.cardSuit;
import poker.models.enums.cardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class deck {
    private ArrayList<card> cards;

    public deck(){
        cards = new ArrayList<card>();
        this.fillDeck();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public hand getCardsHand(){
        Random random = new Random();

        int firstCardIndex = random.nextInt(cards.size());
        card firstCard = this.cards.get(firstCardIndex);
        this.cards.remove(firstCardIndex);

        int secondCardIndex = random.nextInt(cards.size());
        card secondCard = this.cards.get(secondCardIndex);
        cards.remove(secondCardIndex);

        return new hand(firstCard, secondCard);
    }

    private void fillDeck(){
        this.cards.clear(); //SRP
        for(cardSuit suit: cardSuit.values()){
            for(cardValue value: cardValue.values()){
                this.addCard(suit, value);
            }
        }
    }

    private void addCard(cardSuit suit, cardValue value){
        card card = new card(suit, value);
        cards.add(card);
    }
}
