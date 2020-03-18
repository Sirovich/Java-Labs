package Poker.Logic.Games;

import Poker.Models.Table;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {
    private Table table;
    private Stage stage;

    public void StartGame(int playersCount) throws Exception {
        table = new Table();
        table.setPlayers(1, playersCount - 1);
        System.out.println(this.stage);
        this.stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Main.fxml"));
        this.stage.setScene(new Scene(root, 630, 400));
        this.stage.show();
    }

    public void ShowMainMenu(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Menu.fxml"));
        primaryStage.setTitle("PokerDocker");
        primaryStage.setScene(new Scene(root, 630, 400));
        primaryStage.show();
    }

    @FXML
    public void SetUpDuel() throws Exception{
        this.StartGame(2);
    }

    @FXML
    public void SetUpDefault() throws Exception{
        this.StartGame(8);
    }
}
