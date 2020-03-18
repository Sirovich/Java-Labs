package Poker.Models;

import Poker.Models.Cards.Deck;
import Poker.Models.Players.Bot;
import Poker.Models.Players.Player;

import java.util.ArrayList;

public class Table {
    private ArrayList<Player> players;
    private Deck deck;

    public Table(){
        players = new ArrayList<Player>();
        deck = new Deck();
    }

    public void setPlayers(int playersCount, int botCount){
        for(int i = 0; i < playersCount; i++){
            players.add(new Player());
        }

        for(int i = 0; i < botCount; i++){
            players.add(new Bot());
        }
    }
}
