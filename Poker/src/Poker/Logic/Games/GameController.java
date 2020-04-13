package Poker.Logic.Games;

import Poker.Models.Table;
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

    public void SetUpGame(int playersCount) throws Exception {
        Stage stage = (Stage)menuPane.getScene().getWindow();
        table.setPlayers(1, playersCount - 1);
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Main.fxml"));
        stage.setTitle("PokerDocker");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public void StartGame(){
        table.dealCards();
        table.startGame();
    }

    public void ShowMainMenu(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Menu.fxml"));
        primaryStage.setTitle("PokerDocker");
        primaryStage.setScene(new Scene(root, 630, 400));
        primaryStage.show();
    }

    @FXML
    public void SetUpDuel() throws Exception{
        this.SetUpGame(2);
    }

    @FXML
    public void SetUpDefault() throws Exception{
        this.SetUpGame(8);
    }
}
