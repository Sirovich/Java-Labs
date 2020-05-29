package poker.models.enums;

import javafx.scene.image.Image;

import java.io.File;

public enum CardSuit {
    Clubs(1),
    Spades(2),
    Hearts(3),
    Diamonds(4);

    private final int suitValue;
    CardSuit(final int suitValue) {
        this.suitValue = suitValue;
    }

    public int getSuitValue() {
        return this.suitValue;
    }
}
