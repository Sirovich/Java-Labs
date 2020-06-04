package poker.models.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewController {

    public void showMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poker/menu.fxml"));

        AnchorPane root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("PokerDocker");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }
}
