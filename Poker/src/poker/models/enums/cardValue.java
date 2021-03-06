package poker.models.enums;

public enum CardValue {
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Jack(11),
    Queen(12),
    King(13),
    Ace(14);

    private final int cardValue;

    CardValue(final int cardValue) {
        this.cardValue = cardValue;
    }

    public int getCardValue() {
        return this.cardValue;
    }

    public String displayName() {
        return ordinal() < 9 ? String.valueOf(cardValue) : name().substring(0, 1);
    }
}
