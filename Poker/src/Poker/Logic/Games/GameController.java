package Poker.Logic.Games;

import Poker.Models.Table;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameController {
    private Table table;
    @FXML
    private AnchorPane menuPane;

    public GameController(){
        table = new Table();
    }

    public void SetUpGame() throws Exception {
        Stage stage = (Stage)menuPane.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/Poker/Main.fxml"));
        newStage.setTitle("PokerDocker");
        newStage.setScene(new Scene(root, 800, 500));
        newStage.show();
    }

    public void StartGame(){
        Runnable task = () -> {
            table.setPlayers(1, 7);
            table.dealCards();
            table.startGame();
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void ShowMainMenu(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Menu.fxml"));
        primaryStage.setTitle("PokerDocker");
        primaryStage.setScene(new Scene(root, 630, 400));
        primaryStage.show();
    }

    @FXML
    public void SetUpDuel() throws Exception{
        this.SetUpGame();

        StartGame();
    }

    @FXML
    public void SetUpDefault() throws Exception{
        this.SetUpGame();

        StartGame();
    }
}
