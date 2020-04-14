package poker.models.enums;

public enum cardSuit {
    Hearts(1),
    Spades(2),
    Clubs(3),
    Diamonds(4);

    private final int suitValue;

    cardSuit(final int suitValue) {
        this.suitValue = suitValue;
    }

    public int getSuitValue() {
        return this.suitValue;
    }
}
