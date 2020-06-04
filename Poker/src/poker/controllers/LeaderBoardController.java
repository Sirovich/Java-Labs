package poker.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import poker.logics.extensions.readers.Reader;
import poker.logics.extensions.writers.LocalWriter;
import poker.logics.registration.LocalRegistrationLogic;
import poker.logics.registration.RegistrationLogic;
import poker.logics.saves.LocalSaveLogic;
import poker.models.Table;
import poker.models.saves.ModelsComparator;
import poker.models.saves.PlayerSaveModel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class LeaderBoardController {
    private final Reader reader;
    private VBox board;
    private Stage stage;

    public LeaderBoardController(Reader reader){
        this.reader = reader;
    }

    public void setUp(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poker/leaderBoard.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        board = (VBox) loader.getNamespace().get("board");

        List<PlayerSaveModel> saves = reader.readAll();
        Integer i = 1;
        Collections.sort(saves, new ModelsComparator());
        for (PlayerSaveModel save: saves) {
            Label playerInfo = new Label();
            playerInfo.setFont(Font.font("Arial", 25));
            playerInfo.setText(i + ". " + save.getLogin() + " " + save.getMoney() + "$");
            board.getChildren().add(playerInfo);
            i++;
        }

        stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

}
