package poker.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import poker.logics.extensions.readers.LocalReader;
import poker.logics.extensions.writers.LocalWriter;
import poker.logics.registration.LocalRegistrationLogic;
import poker.logics.registration.RegistrationLogic;
import poker.logics.saves.LocalSaveLogic;
import poker.models.Table;

import java.io.IOException;

public class RegistrationController {
    private final RegistrationLogic logic;
    private Button submitButton;
    private TextField loginLabel;
    private PasswordField passwordLabel;
    private Label errorLabel;
    private Stage stage;

    public RegistrationController(){
        logic = new LocalRegistrationLogic(new LocalWriter("save.dat"));
    }

    public RegistrationController(RegistrationLogic logic){
        this.logic = logic;
    }

    public void setUp(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poker/menu.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        submitButton = (Button) loader.getNamespace().get("submitButton");
        loginLabel = (TextField) loader.getNamespace().get("loginLabel");
        passwordLabel = (PasswordField) loader.getNamespace().get("passwordLabel");
        errorLabel = (Label) loader.getNamespace().get("errorLabel");

        submitButton.setOnAction(actionEvent -> {
            register();
        });

        stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void register(){
        int result = logic.register(loginLabel.getText(), passwordLabel.getText());
        if (result == -1){
            errorLabel.setText("Wrong password");
        }
        else if (result == 0){
            errorLabel.setText("Wrong login");
        }
        else{
            stage.close();
            Table table = new Table(new LocalSaveLogic(new LocalWriter("save.dat")), loginLabel.getText(), passwordLabel.getText());
            table.setUp(2500);
        }
    }
}
