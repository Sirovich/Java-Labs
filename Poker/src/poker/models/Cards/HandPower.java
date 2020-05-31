package poker.models.cards;
import poker.models.enums.CardValue;
import poker.models.enums.HandPowerType;

import java.util.List;

public class HandPower implements Comparable<HandPower> {
    private final HandPowerType handPowerType;
    private final List<CardValue> tieBreakingInformation;

    public HandPower(final HandPowerType handPowerType,
                     final List<CardValue> tieBreakingInformation) {
        this.handPowerType = handPowerType;
        this.tieBreakingInformation = tieBreakingInformation;
    }

    public int compareTo(HandPower other) {
        int typeDifference = handPowerType.getPower()
                - other.handPowerType.getPower();
        if (typeDifference == 0) {
            for (int i = 0; i < tieBreakingInformation.size(); i++) {
                int tieDifference = tieBreakingInformation.get(i).getCardValue()
                        - other.tieBreakingInformation.get(i).getCardValue();
                if (tieDifference != 0) {
                    return tieDifference;
                }
            }
            return 0;
        }

        return typeDifference;
    }

    @Override
    public String toString() {
        return handPowerType.toString() + " "
                + tieBreakingInformation.toString();
    }

    public HandPowerType getHandPowerType() {
        return handPowerType;
    }

    public List<CardValue> getTieBreakingInformation() {
        return tieBreakingInformation;
    }
}