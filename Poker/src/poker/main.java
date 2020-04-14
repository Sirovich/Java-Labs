package poker;

import poker.logic.games.gameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {
    private gameController game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        game = new gameController();
        game.showMainMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
