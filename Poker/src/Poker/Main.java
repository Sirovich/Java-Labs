package Poker;

import Poker.Logic.Games.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private GameController game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        game = new GameController();
        game.ShowMainMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
