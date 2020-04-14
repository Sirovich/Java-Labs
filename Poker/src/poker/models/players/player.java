package poker.models.players;

import poker.models.cards.hand;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class player {
    private poker.models.cards.hand hand;
    private int money;
    private int bet;
    private boolean isFold = false;
    private boolean isDealer;

    public player(){
        money = 2500;
        this.hand = new hand();
        isDealer = false;
    }

    public void setHand(poker.models.cards.hand hand) {
        this.hand = hand;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void setFold() {
        isFold = true;
    }

    public void makeMove(int betSize, int bigBlind) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poker/main.fxml"));
        Parent root = loader.load();

        Button bet = (Button)loader.getNamespace().get("bet");
        Button fold = (Button)loader.getNamespace().get("fold");
        Button call = (Button)loader.getNamespace().get("call");
        Button allIn = (Button)loader.getNamespace().get("allIn");


    }


    public int getMoney() {
        return this.money;
    }

    public void placeBet(int betSize) {
        money -= betSize;
        bet += betSize;
    }

    public int getBet() {
        return this.bet;
    }
}
