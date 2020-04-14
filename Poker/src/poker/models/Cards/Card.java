package poker.models.cards;

import poker.models.enums.cardSuit;
import poker.models.enums.cardValue;

public class card {
    private cardValue value;
    private cardSuit suit;

    public card(cardSuit suit, cardValue value){
        this.suit = suit;
        this.value = value;
    }


}
