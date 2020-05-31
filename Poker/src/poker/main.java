package poker;

import javafx.application.Application;
import javafx.stage.Stage;
import poker.models.Table;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Table table = new Table();
        table.setUp();
    }


    public static void main(String[] args) {
        launch(args);
    }
}