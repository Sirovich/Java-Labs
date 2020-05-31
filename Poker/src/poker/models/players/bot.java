package poker.models.players;

import poker.models.cards.*;
import poker.models.enums.CardSuit;
import poker.models.enums.CardValue;
import poker.models.enums.GameStage;
import poker.models.enums.HandPowerType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Bot extends Player {
    private final HandPowerRanker handPowerRanker = new HandPowerRanker();
    private final HandStrengthEvaluator handStrengthEvaluator = new HandStrengthEvaluator();

    @Override
    public void setHand(Hand hand) {
        super.setHand(hand);
    }

    @Override
    public boolean isFold() {
        return super.isFold();
    }

    @Override
    public void makeMove(int bet, GameStage stage, List<Card> boardCards, int activePlayers){
        if (stage == GameStage.flop){
            decidePreFlop(super.getHand(), bet);
        }
        else{
            decideAfterFlop(boardCards, bet, activePlayers);
        }
    }

    private void raise(int bet, double coefficient){
        int total = bet - getBet();
        int raiseBet = (int) Math.round(total + total * coefficient);
        if (raiseBet > getMoney()){
            placeBet(getMoney());
        }
        else{
            placeBet(raiseBet);
        }
    }

    private void call(int bet){
        if (bet - getBet() > getMoney()){
            placeBet(getMoney());
        }
        else{
            placeBet(bet - getBet());
        }
    }

    private void decideAfterFlop(List<Card> boardCards, int bet, int activePlayers) {
        double p = this.handStrengthEvaluator.evaluate(super.getHand().getCards(), boardCards,
                activePlayers);
        Random random = new Random();
        int chance = random.nextInt(100);
        if (p > 0.5 && chance > 50) {
            raise(bet, p);
            return;
        } else if (p > 0.2 && chance > 20) {
            call(bet);
            return;
        }
        setFold();
    }

    private void decidePreFlop(Hand hand, int bet) {
        Card card1 = hand.getFirstCard();
        Card card2 = hand.getSecondCard();
        int sumPower = card1.getValue().getCardValue() + card2.getValue().getCardValue();
        if (card1.getValue().equals(card2.getValue()) && getBet() == 0) {
            System.out.println(getBet());
            raise(bet, sumPower/12);
        } 
        else {
            if (sumPower < 16 && getBet() == 0){
                setFold();
            }
            else{
                call(bet);
            }
        }
    }

    @Override
    public void placeBet(int betSize) {
        super.placeBet(betSize);
    }
}


/*
make move

System.out.println(getHandPower());
        if (stage == GameStage.flop)
        {
            double handPower = getHandPower();
            Random random = new Random();
            if (handPower < 4){
                super.setFold();
                return;
            }
            if (bet == bigBlind) {
                if (random.nextInt(100) > 20) { //Random for save play or hope play
                    if (handPower > 7) {
                        int raiseBet = (int) Math.round(super.getMoney() * (10 * (handPower + handPower - 5) / 800));
                        super.placeBet(raiseBet);
                        return;
                    }
                    else if (bigBlind*100/super.getMoney() <= 10){
                        super.placeBet(bigBlind);
                        return;
                    }
                }
            }
            else
            {
                if (bet - bigBlind < bigBlind || handPower > 8){
                    super.placeBet(bet - getBet());
                    return;
                }
                else{
                    super.setFold();
                    return;
                }
            }
        }




    private int getCombination(Hand hand, ArrayList<Card> board) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(hand.getCards());
        if ((board != null) && (board.size() != 0)) {
            allCards.addAll(board);
        }
        allCards.sort(Comparator.comparingInt(card -> card.getValue().getCardValue()));
        if (isRoyalFlush(allCards) != -1) {
            return 117;
        }
        int result = isStraightFlush(allCards);
        if (result != -1) {
            return 104 + result;
        }
        result = isQuads(allCards);
        if (result != -1) {
            return 91 + result;
        }
        result = isFullHouse(allCards);
        if (result != -1) {
            return 78 + result;
        }
        result = isFlush(allCards);
        if (result != -1) {
            return 65 + result;
        }
        result = isStraight(allCards);
        if (result != -1) {
            return 52 + result;
        }
        result = isSet(allCards);
        if (result != -1) {
            return 39 + result;
        }
        result = isTwoPair(allCards);
        if (result != -1) {
            return 26 + result;
        }
        result = isOnePair(allCards);
        if (result != -1) {
            return 13 + result;
        }
        return isHighCard(allCards);
    }

    private int isHighCard(ArrayList<Card> allCards) {
    }

    private int isOnePair(ArrayList<Card> allCards) {
    }

    private int isTwoPair(ArrayList<Card> allCards) {
    }

    private int isSet(ArrayList<Card> allCards) {
    }

    private int isStraight(ArrayList<Card> allCards) {
    }

    private int isFlush(ArrayList<Card> allCards) {
    }

    private int isFullHouse(ArrayList<Card> allCards) {

    }

    private int isQuads(ArrayList<Card> allCards) {

    }

    private int isStraightFlush(ArrayList<Card> allCards) {
    }

    private int isRoyalFlush(ArrayList<Card> cards){

    }

* */
