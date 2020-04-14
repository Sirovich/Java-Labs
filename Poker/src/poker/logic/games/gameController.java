package poker.logic.games;

import poker.models.table;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class gameController {
    private poker.models.table table;
    @FXML
    private AnchorPane menuPane;

    public gameController(){
        table = new table();
    }

    public void setUpGame() throws Exception {
        Stage stage = (Stage)menuPane.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/poker/main.fxml"));
        newStage.setTitle("PokerDocker");
        newStage.setScene(new Scene(root, 800, 500));
        newStage.show();
    }

    public void startGame(){
        Runnable task = () -> {
            table.setPlayers(1, 7);
            table.dealCards();
            table.startGame();
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void showMainMenu(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/poker/menu.fxml"));
        primaryStage.setTitle("PokerDocker");
        primaryStage.setScene(new Scene(root, 630, 400));
        primaryStage.show();
    }

    @FXML
    public void setUpDuel() throws Exception{
        this.setUpGame();

        startGame();
    }

    @FXML
    public void setUpDefault() throws Exception{
        this.setUpGame();

        startGame();
    }
}
