package poker.models.cards;

import poker.models.enums.CardSuit;
import poker.models.enums.CardValue;
import poker.models.enums.HandPowerType;
import poker.models.extensions.MapList;

import java.util.*;


public class HandPowerRanker {
    private final Comparator<CardValue> cardNumberComparator = new Comparator<CardValue>() {

        public int compare(CardValue cardNumber1, CardValue cardNumber2) {
            return cardNumber1.getCardValue() - cardNumber2.getCardValue();
        }
    };

    public HandPower rank(List<Card> cards) {
        MapList<CardValue, Card> numberGroup = getNumberGroup(cards);
        MapList<CardSuit, Card> suitGroup = getSuitGroup(cards);
        List<Card> cardsSortedByNumber = getCardsSortedByNumber(cards);

        CardValue straightFlushNumber = getStraightFlushNumber(suitGroup);

        // Straight flush
        if (straightFlushNumber != null) {
            return new HandPower(HandPowerType.straightFlush,
                    Arrays.asList(straightFlushNumber));
        }

        CardValue cardNumberForFour = getCardNumberForCount(4, numberGroup);
        // Four of a kind
        if (cardNumberForFour != null) {
            return new HandPower(HandPowerType.fourOfAKind,
                    calculateSameKindTie(4, cardNumberForFour,
                            cardsSortedByNumber));
        }

        List<CardValue> fullHouseCardNumbers = getFullHouse(numberGroup);
        // Full house
        if (fullHouseCardNumbers.size() == 2) {
            return new HandPower(HandPowerType.fullHouse, fullHouseCardNumbers);
        }

        // Flush
        CardSuit flushSuit = getFlush(suitGroup);
        if (flushSuit != null) {
            return new HandPower(HandPowerType.flush, calculateFlushTie(flushSuit, suitGroup));
        }

        // Straight
        CardValue straightNumber = getStraight(numberGroup);
        if (straightNumber != null) {
            return new HandPower(HandPowerType.straight, Arrays.asList(straightNumber));
        }

        // Three of a kind
        CardValue cardNumberForThree = getCardNumberForCount(3, numberGroup);
        if (cardNumberForThree != null) {
            return new HandPower(HandPowerType.threeOfAKind,
                    calculateSameKindTie(3, cardNumberForThree,
                            cardsSortedByNumber));
        }

        // Pair(s)
        CardValue cardNumberForTwo = getCardNumberForCount(2, numberGroup);
        if (cardNumberForTwo != null) {
            List<CardValue> pairsCardNumber = getPairs(numberGroup);
            // Two pair
            if (pairsCardNumber.size() >= 2) {

                return new HandPower(HandPowerType.twoPair,
                        calculateTwoPairsTie(pairsCardNumber,
                                cardsSortedByNumber));
            }
            // One Pair
            else {
                return new HandPower(HandPowerType.onePair,
                        calculateSameKindTie(2, cardNumberForTwo,
                                cardsSortedByNumber));
            }
        }

        // High Card
        return new HandPower(HandPowerType.highCard,
                bestCardsNumberInList(cardsSortedByNumber));
    }

    private List<CardValue> getFullHouse(MapList<CardValue, Card> numberGroup) {
        List<CardValue> fullHouseCardNumbers = new ArrayList<CardValue>();

        List<CardValue> cardNumbers = new ArrayList<CardValue>(
                numberGroup.keySet());
        Collections.sort(cardNumbers, cardNumberComparator);
        Collections.reverse(cardNumbers);

        // Find the best cards for the triple
        for (CardValue cardNumber : cardNumbers) {
            if (numberGroup.get(cardNumber).size() >= 3) {
                fullHouseCardNumbers.add(cardNumber);
                break;
            }
        }

        // Find the best card for the pair
        if (fullHouseCardNumbers.size() > 0) {
            for (CardValue cardNumber : cardNumbers) {
                if (numberGroup.get(cardNumber).size() >= 2
                        && !cardNumber.equals(fullHouseCardNumbers.get(0))) {
                    fullHouseCardNumbers.add(cardNumber);
                    break;
                }
            }
        }

        return fullHouseCardNumbers;
    }

    private List<CardValue> calculateTwoPairsTie(
            List<CardValue> pairsCardNumber, List<Card> cardsSortedByNumber) {
        Collections.sort(pairsCardNumber, cardNumberComparator);
        Collections.reverse(pairsCardNumber);
        List<CardValue> tieBreakingInformation = new ArrayList<CardValue>(
                pairsCardNumber);

        for (int i = cardsSortedByNumber.size() - 1; i >= 0; i--) {
            CardValue cardNumber = cardsSortedByNumber.get(i).getValue();
            if (!pairsCardNumber.contains(cardNumber)) {
                tieBreakingInformation.add(cardNumber);
                return tieBreakingInformation;
            }
        }
        return null;
    }

    private List<CardValue> getPairs(MapList<CardValue, Card> numberGroup) {
        List<CardValue> pairsCardNumber = new ArrayList<CardValue>();
        for (List<Card> cards : numberGroup) {
            if (cards.size() == 2) {
                pairsCardNumber.add(cards.get(0).getValue());
            }
        }
        Collections.sort(pairsCardNumber, cardNumberComparator);
        Collections.reverse(pairsCardNumber);

        if (pairsCardNumber.size() > 2) {
            pairsCardNumber.remove(pairsCardNumber.size() - 1);
        }

        return pairsCardNumber;
    }

    private List<CardValue> calculateFlushTie(CardSuit flushSuit,
                                               MapList<CardSuit, Card> suitGroup) {
        List<Card> cards = suitGroup.get(flushSuit);
        return bestCardsNumberInList(cards);
    }

    private List<CardValue> bestCardsNumberInList(List<Card> cards) {
        List<CardValue> cardNumbers = cardsToCardNumber(cards);
        Collections.sort(cardNumbers, cardNumberComparator);
        Collections.reverse(cardNumbers);
        return cardNumbers.subList(0, 5);
    }

    private List<Card> getCardsSortedByNumber(List<Card> cards) {
        List<Card> cardsSortedByNumber = new ArrayList<Card>(cards);
        Collections.sort(cardsSortedByNumber);

        return cardsSortedByNumber;
    }

    private List<CardValue> calculateSameKindTie(Integer sameKindCount,
                                                 CardValue sameKindCardNumber, List<Card> cardsSortedByNumber) {
        List<CardValue> tieBreakingInformation = new ArrayList<CardValue>();
        tieBreakingInformation.add(sameKindCardNumber);

        int left = 5 - sameKindCount;
        for (int i = cardsSortedByNumber.size() - 1; i >= 0; i--) {
            Card card = cardsSortedByNumber.get(i);

            if (!card.getValue().equals(sameKindCardNumber) && left > 0) {
                tieBreakingInformation.add(card.getValue());
                left--;
            }
        }

        return tieBreakingInformation;
    }

    private CardValue getCardNumberForCount(Integer count,
                                             MapList<CardValue, Card> numberGroup) {
        for (Map.Entry<CardValue, List<Card>> entry : numberGroup.entrySet()) {
            if (entry.getValue().size() == count) {
                return entry.getKey();
            }
        }
        return null;
    }

    private CardValue getStraight(MapList<CardValue, Card> numberGroup) {
        List<CardValue> cardNumbers = new ArrayList<CardValue>(
                numberGroup.keySet());
        return getStraightNumber(cardNumbers);
    }

    private CardValue getStraightFlushNumber(MapList<CardSuit, Card> suitGroup) {
        CardSuit flushSuit = getFlush(suitGroup);
        if (flushSuit == null) {
            return null;
        }

        List<Card> cards = suitGroup.get(flushSuit);
        List<CardValue> cardNumbers = cardsToCardNumber(cards);

        return getStraightNumber(cardNumbers);
    }

    private List<CardValue> cardsToCardNumber(List<Card> cards) {
        List<CardValue> cardNumbers = new ArrayList<CardValue>();

        for (Card card : cards) {
            cardNumbers.add(card.getValue());
        }
        return cardNumbers;
    }

    private CardValue getStraightNumber(List<CardValue> cardNumbers) {
        CardValue straightNumber = null;
        int straightCount = 1;
        int prevPower = 0;
        Collections.sort(cardNumbers, cardNumberComparator);
        for (CardValue cardNumber : cardNumbers) {
            if (cardNumber.getCardValue() == prevPower + 1) {
                straightCount++;
                if (straightCount >= 5) {
                    straightNumber = cardNumber;
                }
            } else {
                straightCount = 1;
            }
            prevPower = cardNumber.getCardValue();
        }
        return straightNumber;
    }

    private CardSuit getFlush(MapList<CardSuit, Card> suitGroup) {
        for (List<Card> cards : suitGroup) {
            if (cards.size() >= 5) {
                return cards.get(0).getSuit();
            }
        }
        return null;
    }

    private MapList<CardValue, Card> getNumberGroup(List<Card> cards) {
        MapList<CardValue, Card> numberGroup = new MapList<CardValue, Card>();
        for (Card card : cards) {
            numberGroup.add(card.getValue(), card);
        }
        return numberGroup;
    }

    private MapList<CardSuit, Card> getSuitGroup(List<Card> cards) {
        MapList<CardSuit, Card> suitGroup = new MapList<CardSuit, Card>();
        for (Card card : cards) {
            suitGroup.add(card.getSuit(), card);
        }
        return suitGroup;
    }
}