package Poker.Logic.Games;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowController {
    private Stage stage;

    public WindowController(Stage stage){
        this.stage = stage;
    }

    public void ShowMainMenu(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Menu.fxml"));
        this.stage.setTitle("PokerDocker");
        this.stage.setScene(new Scene(root, 630, 400));
        this.stage.show();
    }

    public void ShowGame() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Poker/Main.fxml"));
        this.stage.setScene(new Scene(root, 630, 400));
        this.stage.show();
    }

    public void CloseCurrentWindow(){
        this.stage.close();
    }
}
