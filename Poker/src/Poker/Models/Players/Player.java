package Poker.Models.Players;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Player {
    @FXML
    private HBox playerHand;
    private boolean isDealer;

    public Player(){
        isDealer = false;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }
}
