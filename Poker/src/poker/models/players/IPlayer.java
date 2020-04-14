package poker.models.players;

import poker.models.cards.hand;

public interface IPlayer {

    void setHand(hand hand);

    boolean isFold();

    void setFold();

    void makeMove();

    int getMoney();

    void placeBet(int betSize);
}
