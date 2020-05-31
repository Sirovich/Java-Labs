package poker.models.enums;

public enum HandPowerType {
    highCard(1),
    onePair(2),
    twoPair(3),
    threeOfAKind(4),
    straight(5),
    flush(6),
    fullHouse(7),
    fourOfAKind(8),
    straightFlush(9);

    private final int power;

    private HandPowerType(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
