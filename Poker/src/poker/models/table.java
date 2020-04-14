package poker.models;

import poker.models.cards.deck;
import poker.models.players.bot;
import poker.models.players.player;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class table {
    private ArrayList<player> players;
    private ArrayList<Integer> bets;

    @FXML
    private HBox playerHand;

    private int smallBlind = 100;
    private int bigBlind = 200;
    private int pot = 0;
    private int currentStageBets = 0;
    private int stage = 0;
    private int activePlayers;


    private poker.models.cards.deck deck;
    private int dealerId;

    public table(){
        players = new ArrayList<player>();
        bets = new ArrayList<Integer>();
        deck = new deck();
    }

    public void setPlayers(int playersCount, int botCount){
        for(int i = 0; i < playersCount; i++){
            players.add(new player());
            bets.add(0);
        }

        for(int i = 0; i < botCount; i++){
            players.add(new bot());
            bets.add(0);
        }

        Random random = new Random();
        dealerId = random.nextInt(players.size());

        activePlayers = players.size();
    }

    public void dealCards(){
        this.setDealerId();
        this.deck.shuffle();
        for (poker.models.players.player player: players) {
            player.setHand(deck.getCardsHand());
        }
    }

    public void startGame() {
        int current = dealerId;

        bets.set(current, bigBlind);
        players.get(current).placeBet(bigBlind);
        current = nextPlayer(current);


        players.get(current).placeBet(smallBlind);
        bets.set(current, smallBlind);
        current = nextPlayer(current);

        while (activePlayers > 1)
        {
            player player = players.get(current);
            if (!player.isFold()) {
                try {
                    player.makeMove(getMaxBet(), bigBlind);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(-1488);
                }
            }

            current = nextPlayer(current);
        }

    }

    private int nextPlayer(int current){
        if (current == players.size() - 1){
            current = 0;
        }
        else
        {
            current++;
        }

        return current;
    }

    private int getMaxBet(){
        int max = bigBlind;
        for (int bet: bets) {
            if (bet > max){
                max = bet;
            }
        }

        return max;
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
