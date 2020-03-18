package Poker.Models.Enums;

public enum CardSuit {
    Hearts(1),
    Spades(2),
    Clubs(3),
    Diamonds(4);

    private final int suitValue;

    CardSuit(final int suitValue) {
        this.suitValue = suitValue;
    }

    public int getSuitValue() {
        return this.suitValue;
    }
}
