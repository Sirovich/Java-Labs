package Poker.Models.Cards;

import Poker.Models.Enums.CardSuit;
import Poker.Models.Enums.CardValue;

public class Card {
    private CardValue value;
    private CardSuit suit;

    public Card(CardSuit suit, CardValue value){
        this.suit = suit;
        this.value = value;
    }


}
