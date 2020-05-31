package poker.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import poker.models.cards.Card;
import poker.models.cards.Deck;
import poker.models.cards.HandPower;
import poker.models.cards.HandPowerRanker;
import poker.models.enums.GameStage;
import poker.models.players.Bot;
import poker.models.players.Player;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static poker.models.enums.GameStage.flop;

public class Table {
    private ArrayList<Player> players;
    private ArrayList<Integer> bets;
    private ArrayList<Card> tableCards;
    @FXML
    private HBox playerHand;

    private final static int smallBlind = 100;
    private final static int bigBlind = 200;
    private GameStage currentStage = flop;
    private int activePlayers;
    private int current;
    private Deck deck;
    private int dealerId;
    private Timeline timeline;
    private FXMLLoader loader;
    private boolean isEndTurn = true;
    private int lastTurnPlayerId;
    private HandPowerRanker handPowerRanker;
    private Button startButton;
    private boolean isEndOfLap;

    public Table(){
        players = new ArrayList<Player>();
        bets = new ArrayList<Integer>();
        tableCards = new ArrayList<Card>();
        deck = new Deck();
        handPowerRanker = new HandPowerRanker();
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
        current = dealerId;
        current = getNextPlayerId(current);
        players.get(current).placeBet(smallBlind);
        bets.set(current, smallBlind);
        drawBet();
        current = getNextPlayerId(current);
        lastTurnPlayerId = current;
        players.get(current).placeBet(bigBlind);
        bets.set(current, bigBlind);
        drawBet();
        current = getNextPlayerId(current);
        activePlayers = players.size();
    }

    public void dealCards(){
        this.deck.shuffle();
        for (Player player: players) {
            player.setHand(deck.getCardsHand());
        }
    }

    public void drawHands(){
        for(int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getClass().getTypeName().equals(Player.class.getName())) {
                Card firstCard = players.get(i).getHand().getFirstCard();
                Card secondCard = players.get(i).getHand().getSecondCard();
                int cardNumber = (firstCard.getSuit().getSuitValue() -1) * 13 + firstCard.getValue().getCardValue() - 1;
                File file = new File("src/Resources/Images/" + cardNumber + ".png");
                Image image = new Image(file.toURI().toString(), 60, 160, true, true);
                ImageView view = new ImageView(image);
                HBox box = (HBox)loader.getNamespace().get("playerHand");
                box.getChildren().clear();
                box.getChildren().add(view);

                cardNumber = (secondCard.getSuit().getSuitValue() -1) * 13 + secondCard.getValue().getCardValue() - 1;
                file = new File("src/Resources/Images/" + cardNumber + ".png");
                image = new Image(file.toURI().toString(), 60, 160, true, true);
                view = new ImageView(image);
                box.getChildren().add(view);
            }
            else{
                File file = new File("src/Resources/Images/back.png");
                Image image = new Image(file.toURI().toString(), 60, 160, true, true);
                ImageView view = new ImageView(image);
                HBox box = (HBox)loader.getNamespace().get("hand"+i);
                box.getChildren().clear();
                box.getChildren().addAll(view, new ImageView(image));
            }
        }

    }

    private void drawMoney(){
        for(int i = 0; i < players.size(); i++){
            Label label = (Label)loader.getNamespace().get("money"+i);
            label.setText(players.get(i).getMoney()+"$");
        }
    }

    public void setUp(){
        Stage newStage = new Stage();
        loader = new FXMLLoader(getClass().getResource("/poker/main.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPlayers(1, 7);
        dealCards();
        drawHands();
        drawMoney();
        startButton = (Button)loader.getNamespace().get("startButton");

        Runnable task = () -> {
            while(true) {
                while (!isEndTurn) {
                    try {
                        if (Thread.currentThread().isInterrupted())
                        {
                            return;
                        }
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                isEndTurn = false;
                startButton.fire();
            }
        };

        startButton.setOnAction(event->
        {
            startButton.setOpacity(0);
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            Platform.runLater(() -> {
                                executeStage();
                                if (current != 0) {
                                    if (!players.get(current).isFold()) {
                                        drawBet();
                                    } else {
                                        drawFold();
                                    }
                                }
                                if (current != 0) {
                                    current = getNextPlayerId(current);
                                    isEndTurn = true;
                                }
                            });
                            return null;
                        }
                    };
                }
            };
            service.start();
        });

        Thread thread = new Thread(task);
        thread.start();

        setUpUserPanel(loader);

        newStage.setTitle("PokerDocker");
        newStage.setScene(new Scene(root, 853, 664));
        newStage.setOnCloseRequest(windowEvent -> {
            thread.interrupt();
        });
        newStage.show();
    }

    private void lockUserPanel(boolean state){
        Button foldButton = (Button)loader.getNamespace().get("foldButton");
        Button betButton = (Button)loader.getNamespace().get("betButton");
        Button callButton = (Button)loader.getNamespace().get("callButton");
        Button allInButton = (Button)loader.getNamespace().get("allInButton");

        foldButton.setDisable(state);
        betButton.setDisable(state);
        callButton.setDisable(state);
        allInButton.setDisable(state);
    }

    private void executeStage(){
        calculateActivePlayersAmount();
        if (activePlayers > 1) {
            if (getPreviousPlayerId(current) == lastTurnPlayerId && isEndOfLap) {
                if (isAllBetsEqual()){
                    current = getPreviousPlayerId(current);
                    isEndOfLap = false;
                    switch (currentStage){
                        case flop: {
                            dealTableCards(3);
                            currentStage = GameStage.turn;
                            break;
                        }
                        case turn:{
                            dealTableCards(1);
                            currentStage = GameStage.river;
                            break;
                        }
                        case river:{
                            dealTableCards(1);
                            currentStage = GameStage.end;
                            break;
                        }
                        case end:{
                            getWinner();
                            break;
                        }
                    }
                    if (current == 0){
                        isEndTurn = true;
                    }
                }
                else{
                    nextTurn();
                }

            }
            else{
                nextTurn();
            }
        }
        else{
            int pot = getTotalPot();
            for (int i = 0; i < players.size(); i++){
                if (!players.get(i).isFold()){
                    System.out.println("Stay solo:" + i);
                    players.get(i).addMoney(pot);
                    break;
                }
            }
            startNewRound();
        }
    }

    private void calculateActivePlayersAmount() {
        int active = 0;
        for(int i = 0; i < players.size(); i++){
            if (!players.get(i).isFold()){
                active++;
            }
        }
        activePlayers = active;
    }

    private void getWinner() {
        int winnerId = -1;
        HandPower maxPower = null;
        for(int i = 0; i < players.size(); i++){
            if (!players.get(i).isFold()){
                ArrayList<Card> cards =  new ArrayList<Card>(tableCards);
                cards.addAll(players.get(i).getHand().getCards());
                HandPower power = handPowerRanker.rank(cards);
                if (winnerId == -1){
                    maxPower = power;
                    winnerId = i;
                    continue;
                }
                if (power.compareTo(maxPower) > 0){
                    winnerId = i;
                    maxPower = power;
                }
            }
        }

        players.get(winnerId).addMoney(getTotalPot());
        System.out.println("Player win: "+winnerId);
        startNewRound();
    }

    private void removeTableCards(){
        HBox tabelField = (HBox)loader.getNamespace().get("tableField");
        tabelField.getChildren().clear();
    }

    private void removeBets(){
        for(int i = 0; i < players.size(); i++){
            Label betLabel = (Label) loader.getNamespace().get("bet" + i);
            Integer bet = bets.get(i);
            betLabel.setText(bet.toString());
        }
    }

    private void startNewRound(){
        deck.fillDeck();
        deck.shuffle();
        for(int i = 0; i < players.size(); i++){
            bets.set(i, 0);
            players.get(i).setHand(deck.getCardsHand());
            players.get(i).setFold();
            players.get(i).setActive();
        }

        drawHands();
        drawMoney();
        removeTableCards();
        removeBets();
        currentStage = flop;
        isEndOfLap = true;
        dealerId = getNextPlayerId(dealerId);
        current = dealerId;
        current = getNextPlayerId(current);
        players.get(current).placeBet(smallBlind);
        bets.set(current, smallBlind);
        drawBet();
        current = getNextPlayerId(current);
        lastTurnPlayerId = current;
        players.get(current).placeBet(bigBlind);
        bets.set(current, bigBlind);
        drawBet();
        activePlayers = players.size();
    }

    private int getTotalPot(){
        int pot = 0;
        int playerId = 0;
        for (int i = 0; i < players.size(); i++){
            pot += bets.get(i).intValue();
        }

        return pot;
    }

    private void dealTableCards(int count){
        for (int i = 0; i < count; i++){
            Card card = deck.getRandomCard();
            tableCards.add(card);
            drawTableCard(card);
        }
    }

    private void drawTableCard(Card card){
        HBox tabelField = (HBox)loader.getNamespace().get("tableField");
        int cardNumber = (card.getSuit().getSuitValue() -1) * 13 + card.getValue().getCardValue() - 1;
        File file = new File("src/Resources/Images/" + cardNumber + ".png");
        Image image = new Image(file.toURI().toString(), 60, 160, true, true);
        ImageView view = new ImageView(image);
        tabelField.getChildren().add(view);
    }

    private boolean isAllBetsEqual(){
        int currentBet = getMaxBet();
        for(int i = 0; i < players.size(); i++) {
            if (players.get(i).isFold()){
                continue;
            }

            if (bets.get(i) != currentBet){
                return false;
            }
        }

        return true;
    }

    private void drawBet(){
        Label betLabel = (Label) loader.getNamespace().get("bet" + current);
        Label moneyLabel = (Label) loader.getNamespace().get("money" + current);

        Integer bet = bets.get(current);
        moneyLabel.setText(players.get(current).getMoney()+"$");
        betLabel.setText(bet.toString());
    }

    private void drawFold(){
        Label betLabel = (Label) loader.getNamespace().get("bet" + current);
        Integer bet = bets.get(current);
        betLabel.setText(bet.toString() + " Fold");
    }

    private void setUpUserPanel(FXMLLoader loader){
        Button foldButton = (Button)loader.getNamespace().get("foldButton");
        Button betButton = (Button)loader.getNamespace().get("betButton");
        Button callButton = (Button)loader.getNamespace().get("callButton");
        Button allInButton = (Button)loader.getNamespace().get("allInButton");
        Slider betSlider = (Slider)loader.getNamespace().get("betSlider");
        TextField betValue = (TextField)loader.getNamespace().get("betValue");

        betSlider.setMax(players.get(0).getMoney());
        betSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue)
            {
                betSlider.setMax(players.get(0).getMoney());
                Integer bet = newValue.intValue();
                betValue.setText(bet.toString());
            }
        });


        allInButton.setOnAction(event -> {
            int bet = players.get(0).getMoney();
            players.get(0).placeBet(bet);
            bets.set(0, players.get(0).getBet());
            players.get(0).setEndTurn(true);
            drawBet();
            current = getNextPlayerId(current);
        });

        callButton.setOnAction(event -> {
            players.get(0).placeBet(getMaxBet() - bets.get(0));
            bets.set(0, players.get(0).getBet());
            players.get(0).setEndTurn(true);
            drawBet();
            current = getNextPlayerId(current);
        });

        foldButton.setOnAction(event -> {
            players.get(0).setFold();
            players.get(0).setEndTurn(true);
            drawBet();
            current = getNextPlayerId(current);
        });

        betButton.setOnAction(event -> {
            int value = 0;
            try {
                value = Integer.parseInt(betValue.getText());
            }
            catch (NumberFormatException ex) {

            }
            players.get(0).placeBet(value);
            bets.set(0, bets.get(0) + value);
            players.get(0).setEndTurn(true);
            betSlider.setValue(0);
            drawBet();
            current = getNextPlayerId(current);
        });
    }

    private void nextTurn(){
        isEndOfLap = true;
        if (!players.get(current).isFold()) {
            if (current != 0) {
                players.get(current).makeMove(getMaxBet(), currentStage, tableCards, activePlayers);
                if (!players.get(current).isFold()) {
                    bets.set(current, players.get(current).getBet());
                }
                else if (current == lastTurnPlayerId){
                    int id = getNextPlayerId(lastTurnPlayerId);
                    while (players.get(id).isFold()){
                        id = getNextPlayerId(id);
                    }
                    lastTurnPlayerId = id;
                    System.out.println("Last turn player: "+ id);
                }
            }
            else{
                int[] time = {30};
                lockUserPanel(false);
                Runnable task = () -> {
                    timeline = new Timeline(
                            new KeyFrame(
                                    Duration.millis(1000), //1 сек
                                    ae -> {
                                        if (!players.get(0).isEndTurn()) {
                                            Platform.runLater(() -> {
                                                Label betLabel = (Label) loader.getNamespace().get("timer");
                                                Integer value = time[0];
                                                betLabel.setText(value.toString());
                                            });
                                            time[0] -= 1;
                                        }
                                        else{
                                            stopTimer();
                                        }
                                    }
                            )
                    );
                    timeline.setOnFinished(event ->{
                        players.get(0).setFold();
                        drawBet();
                        current = getNextPlayerId(current);
                        stopTimer();
                    });
                    timeline.setCycleCount(30);
                    timeline.play();
                };

                Thread thread = new Thread(task);
                thread.start();
            }
        }
        else if (current == 0){
            current = getNextPlayerId(current);
            isEndTurn = true;
        }
    }

    private void stopTimer(){
        players.get(0).setEndTurn(false);
        timeline.stop();
        lockUserPanel(true);
        isEndTurn = true;
    }

    private int getPreviousPlayerId(int id){
        if (id == 0){
            return players.size() - 1;
        }
        else
        {
            return id - 1;
        }
    }

    private int getNextPlayerId(int id){
        if (id == players.size() - 1){
            return 0;
        }
        else
        {
            return id + 1;
        }
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
}
