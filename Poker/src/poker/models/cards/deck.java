package poker.models.cards;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import poker.models.enums.CardSuit;
import poker.models.enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<Card>();
        this.fillDeck();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card getRandomCard(){
        Random random = new Random();
        int cardIndex = random.nextInt(cards.size());
        Card card = cards.get(cardIndex);
        cards.remove(cardIndex);
        return card;
    }

    public void removeCard(Card card) {
         cards.remove(card);
    }

    public List<List<Card>> fromDeckToCouplesOfCard(){
        List<List<Card>> couplesOfCard = new ArrayList<List<Card>>();
        int i,j;
        for(i = 0; i < this.cards.size(); i++){
            for (j = i+1; j < this.cards.size(); j++){
                List<Card> tmpCards = new ArrayList<Card>();
                tmpCards.add(this.cards.get(i));
                tmpCards.add(this.cards.get(j));
                couplesOfCard.add(tmpCards);
            }
        }
        return couplesOfCard;
    }

    public Hand getCardsHand(){
        Random random = new Random();

        int firstCardIndex = random.nextInt(cards.size());
        Card firstCard = this.cards.get(firstCardIndex);
        this.cards.remove(firstCardIndex);

        int secondCardIndex = random.nextInt(cards.size());
        Card secondCard = this.cards.get(secondCardIndex);
        cards.remove(secondCardIndex);
        Hand hand = new Hand(firstCard, secondCard);
        return hand;
    }

    public void fillDeck(){
        this.cards.clear();
        for(CardSuit suit: CardSuit.values()){
            for(CardValue value: CardValue.values()){
                this.addCard(suit, value);
            }
        }
    }

    private void addCard(CardSuit suit, CardValue value){
        Card card = new Card(suit, value);
        cards.add(card);
    }
}
