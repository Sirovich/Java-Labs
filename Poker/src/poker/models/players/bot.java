package poker.models.players;

import poker.models.cards.Card;
import poker.models.cards.Hand;
import poker.models.enums.CardSuit;
import poker.models.enums.CardValue;
import poker.models.enums.GameStage;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player {


    @Override
    public void setHand(Hand hand) {
        super.setHand(hand);
    }

    @Override
    public boolean isFold() {
        return super.isFold();
    }

    @Override
    public void makeMove(int bet, int bigBlind, GameStage stage){
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
                if (random.nextInt(10000) > 7000) { //Random for save play or hope play
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
        }
        /*
        *         if (bet > super.getBet()){
            if (bet - super.getBet() > super.getMoney())
            {
                placeBet(super.getMoney());
            }
            else{
                placeBet(bet - super.getBet());
            }
        }*/
    }

    @Override
    public void placeBet(int betSize) {
        super.placeBet(betSize);
    }

    private double getHandPower(){
        Card firstCard = super.getHand().getFirstCard();
        Card secondCard = super.getHand().getSecondCard();
        CardValue value1 = firstCard.getValue();
        CardValue value2 = secondCard.getValue();
        CardSuit suit1 = firstCard.getSuit();
        CardSuit suit2 = secondCard.getSuit();
        CardValue maxRank;
        if (value1.getCardValue() > value2.getCardValue()){
            maxRank = value1;
        }
        else
        {
            maxRank = value2;
        }
        double strength = 0;
        // High card
        if(maxRank == CardValue.Ace) strength += 10;
        else if(maxRank == CardValue.King) strength += 8;
        else if(maxRank == CardValue.Queen) strength += 7;
        else if(maxRank == CardValue.Jack) strength += 6;
        else strength += ((maxRank.getCardValue() + 2) / 2.0);
        // Pairs
        if(value1 == value2) {
            int minimum = 5;
            if(value1  == CardValue.Five) {
                minimum = 6;
            }
            strength = Math.max(strength * 2, minimum);
        }
        // Suited
        if(suit1 == suit2) strength += 2;
        // Closeness
        int gap = maxRank.getCardValue() - Math.min(value1.getCardValue(), value2.getCardValue()) - 1;
        switch(gap) {
            case -1: break;
            case 0: strength++; break;
            case 1: strength--; break;
            case 2: strength -= 2; break;
            case 3: strength -= 4; break;
            default: strength -= 5; break;
        }
        if(gap == 1 && maxRank.getCardValue() < CardValue.Queen.getCardValue()) strength++;
        return strength;
    }
}
