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
    private ArrayList<Player> players; // List interface
    private ArrayList<Integer> bets; // List interface
    private ArrayList<Card> tableCards;
    @FXML
    private HBox playerHand;

    private final static int smallBlind = 100;
    private int bigBlind = 200;
    private int pot = 0;
    private GameStage currentStage = flop;
    private int stage = 0;
    private int activePlayers;
    private int current;
    private Deck deck;
    private int dealerId;
    private Timeline timeline;
    private FXMLLoader loader;
    private boolean isEndTurn = false;

    public Table(){ //add List constructor injection
        players = new ArrayList<Player>();
        bets = new ArrayList<Integer>();
        tableCards = new ArrayList<Card>();
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
        current = dealerId;
        current = nextPlayer();
        activePlayers = players.size();
    }

    public void dealCards(){
        this.deck.shuffle();
        for (Player player: players) {
            player.setHand(deck.getCardsHand());
        }
    }

    public void drawHands(FXMLLoader loader){
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
                box.getChildren().addAll(view, new ImageView(image));
            }
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
        drawHands(loader);
        Button startButton = (Button)loader.getNamespace().get("startButton");

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
                                    drawBet(loader);
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
        newStage.setScene(new Scene(root, 777, 568));
        newStage.setOnCloseRequest(windowEvent -> {
            thread.interrupt();
        });
        newStage.show();
    }

    private void executeStage(){
        if (activePlayers > 1) {
            if (getNextPlayerId(dealerId) == getPreviousPlayerId(current)) {
                for (int i = 0; i < bets.size(); i++)
                {
                    System.out.println(bets.get(i).intValue());
                }
                if (isAllBetsEqual()){
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
                            //getWinner();
                            break;
                        }
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
        int cardNumber = (card.getSuit().getSuitValue() -1) * 13 + card.getValue().getCardValue();
        File file = new File("E:/projects/Проекты 2019-2020/IdeaProjects/Poker/src/Resources/Images/" + cardNumber + ".png");
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

    private void drawBet(FXMLLoader loader){
        System.out.println("Player: "+current);
        Label betLabel = (Label) loader.getNamespace().get("bet" + current);
        Integer bet = players.get(current).getBet();
        betLabel.setText(bet.toString());
        current = nextPlayer();
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
            System.out.println("all in");
            players.get(0).setEndTurn(true);
            drawBet(loader);
        });

        callButton.setOnAction(event -> {
            players.get(0).placeBet(getMaxBet() - bets.get(0));
            bets.set(0, players.get(0).getBet());
            System.out.println("call");
            players.get(0).setEndTurn(true);
            drawBet(loader);
        });

        foldButton.setOnAction(event -> {
            players.get(0).setFold();
            bets.set(0, players.get(0).getBet());
            System.out.println("fold");
            players.get(0).setEndTurn(true);
            drawBet(loader);
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
            System.out.println("bet");
            players.get(0).setEndTurn(true);
            betSlider.setValue(0);
            drawBet(loader);
        });
    }

    public void startGame() {
        int current = dealerId;

        bets.set(current, bigBlind);
        players.get(current).placeBet(bigBlind);
        current = nextPlayer();

        players.get(current).placeBet(smallBlind);
        bets.set(current, smallBlind);
        current = nextPlayer();

        while (activePlayers > 1)
        {
            Player player = players.get(current);
            if (!player.isFold()) {
                player.makeMove(getMaxBet(), bigBlind,currentStage);
            }

            current = nextPlayer();
        }

    }

    private void nextTurn(){
        if (!players.get(current).isFold()) {
            if (current != 0) {
                players.get(current).makeMove(getMaxBet(), bigBlind, currentStage);
                bets.set(current, players.get(current).getBet());
            }
            else{
                int[] time = {30};

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
                                            System.out.println(time[0]);
                                            time[0] -= 1;
                                        }
                                        else{
                                            System.out.println("User pressed button");
                                            stopTimer();
                                        }
                                    }
                            )
                    );
                    timeline.setOnFinished(event ->{
                        stopTimer();
                    });
                    timeline.setCycleCount(30);
                    timeline.play();
                };

                Thread thread = new Thread(task);
                thread.start();
            }
        }
    }

    private void stopTimer(){
        players.get(0).setEndTurn(false);
        timeline.stop();
        isEndTurn = true;
    }

    private int nextPlayer(){
        if (current == players.size() - 1){
            current = 0;
        }
        else
        {
            current++;
        }

        return current;
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

    private void setDealerId(){
        if (dealerId == players.size()) {
            dealerId = 0;
        }
        else {
            dealerId++;
        }
    }
}
