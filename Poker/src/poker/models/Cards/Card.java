package poker.models.cards;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import poker.models.enums.CardSuit;
import poker.models.enums.CardValue;

import java.io.File;

public class Card implements Comparable<Card> {
    private CardValue value;
    private CardSuit suit;

    public Card(CardSuit suit, CardValue value){
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return suit.toString() + value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }

        Card other = (Card) obj;

        return suit.equals(other.suit) && value.equals(other.value);
    }


    public int compareTo(Card card) {
        return value.getCardValue() - card.value.getCardValue();
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }
}
