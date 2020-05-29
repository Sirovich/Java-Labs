package poker.logic.games;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import poker.models.Table;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import poker.models.players.Player;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class GameController {
    private Table table;

    @FXML
    private Button startButton;

    public GameController(){
        table = new Table();
    }

    public void setUpGame() throws Exception {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poker/main.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            table.setPlayers(1, 7);
            table.dealCards();
            //table.drawHands();
            startButton = (Button)loader.getNamespace().get("startButton");
            startButton.setOnAction(event->
            {
               // startGame();
            });
            newStage.setTitle("PokerDocker");
            newStage.setScene(new Scene(root, 800, 500));
            newStage.show();
    }

    public void startGame(Stage newStage){
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
    }

    @FXML
    public void setUpDefault() throws Exception{
        this.setUpGame();

        //startGame();
    }
}
