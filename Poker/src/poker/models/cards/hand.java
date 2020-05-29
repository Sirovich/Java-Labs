package poker.models.cards;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand(Card first, Card second) {
        cards = new ArrayList<Card>();
        cards.add(first);
        cards.add(second);
    }

    public void addCard(Card card){
        if (card == null){
            throw new NullPointerException();
        }

        cards.add(card);
    }

    public void clearHand(){
        cards.clear();
    }

    public Card getFirstCard(){
        return cards.get(0);
    }

    public Card getSecondCard(){
        return cards.get(1);
    }
}
