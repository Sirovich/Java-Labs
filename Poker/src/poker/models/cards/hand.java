package poker.models.cards;

import java.util.ArrayList;

public class hand {
    private ArrayList<card> cards;

    public hand(){
        this.cards = new ArrayList<card>();
    }

    public hand(card first, card second) {
        this.cards = new ArrayList<>();
        cards.add(first);
        cards.add(second);
    }

    public void addCard(card card){
        if (card == null){
            throw new NullPointerException();
        }

        this.cards.add(card);
    }

    public void clearHand(){
        cards.clear();
    }
}
