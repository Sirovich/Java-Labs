package poker.models.players;

import poker.models.cards.Hand;

public interface IPlayer {

    void setHand(Hand hand);

    boolean isFold();

    void setFold();

    void makeMove();

    int getMoney();

    void placeBet(int betSize);
}
