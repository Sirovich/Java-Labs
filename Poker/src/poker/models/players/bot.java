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