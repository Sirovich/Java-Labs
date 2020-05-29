package poker;

import poker.logic.games.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import poker.models.Table;

public class Main extends Application {
    private GameController game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Table table = new Table();
        table.setUp();
    }


    public static void main(String[] args) {
        launch(args);
    }
}