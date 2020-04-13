package Poker.Models.Players;

import Poker.Models.Cards.Hand;

public class Bot extends Player{
    @Override
    public void setHand(Hand hand) {

    }

    @Override
    public boolean isFold() {
        return false;
    }

    @Override
    public void makeMove(int bet, int bigBlind) {

    }

    @Override
    public void placeBet(int betSize) {

    }
}
