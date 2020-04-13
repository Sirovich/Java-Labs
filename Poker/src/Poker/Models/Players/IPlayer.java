package Poker.Models.Players;

import Poker.Models.Cards.Hand;

public interface IPlayer {

    void setHand(Hand hand);

    boolean isFold();

    void setFold();

    void makeMove();

    int getMoney();

    void placeBet(int betSize);
}
