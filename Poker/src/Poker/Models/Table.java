package Poker.Models;

import Poker.Models.Cards.Deck;
import Poker.Models.Players.Bot;
import Poker.Models.Players.IPlayer;
import Poker.Models.Players.Player;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Random;

public class Table {
    private ArrayList<IPlayer> players;
    private ArrayList<Integer> bets;

    @FXML
    private HBox playerHand;

    private int smallBlind = 100;
    private int bigBlind = 200;
    private int totalBet = 0;

    private Deck deck;
    private int dealerId;

    public Table(){
        players = new ArrayList<IPlayer>();
        bets = new ArrayList<Integer>();
        deck = new Deck();
    }

    public void setPlayers(int playersCount, int botCount){
        for(int i = 0; i < playersCount; i++){
            players.add(new Player());
            bets.add(0);
        }

        for(int i = 0; i < botCount; i++){
            players.add(new Bot());
            bets.add(0);
        }

        Random random = new Random();
        dealerId = random.nextInt(players.size());
    }

    public void dealCards(){
        this.setDealerId();
        this.deck.Shuffle();
        for (IPlayer player: players) {
            player.setHand(deck.getCardsHand());
        }
    }

    public void startGame(){
        int current = dealerId;

        bets.set(current, bigBlind);
        players.get(current).placeBet(bigBlind);

        if (current == players.size() - 1){
            current = 0;
        }
        else
        {
            current++;
        }

        players.get(current).placeBet(smallBlind);
        bets.set(current, smallBlind);
    }

    private void setDealerId(){
        if (dealerId == players.size()) {
            dealerId = 0;
        }
        else {
            dealerId++;
        }
    }
}
