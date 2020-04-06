package Poker.Models.Players;

import Poker.Models.Cards.Hand;

public interface IPlayer {

    void setHand(Hand hand);

    boolean isDealer();

    void makeMove();

    void placeBet(int betSize);
}
