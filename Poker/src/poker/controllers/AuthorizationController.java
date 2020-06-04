package poker.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poker.logics.authorizations.AuthorizationLogic;
import poker.logics.authorizations.LocalAuthorizationLogic;
import poker.logics.extensions.readers.LocalReader;
import poker.logics.extensions.writers.LocalWriter;
import poker.logics.saves.LocalSaveLogic;
import poker.models.Table;
import poker.models.saves.PlayerSaveModel;

import java.io.IOException;

public class AuthorizationController {
    private TextField loginLabel;
    private PasswordField passwordLabel;
    private Label errorLabel;
    private Button authorizationButton;
    private Stage stage;
    private Button registerButton;
    private Button leaderBoardButton;
    private final AuthorizationLogic logic;

    public AuthorizationController(){
        logic = new LocalAuthorizationLogic(new LocalReader("save.dat"));
    }

    public AuthorizationController(AuthorizationLogic logic){
        this.logic = logic;
    }

    public void setUp(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poker/authorization.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        authorizationButton = (Button) loader.getNamespace().get("authorizationButton");
        registerButton = (Button) loader.getNamespace().get("registerButton");
        errorLabel = (Label) loader.getNamespace().get("errorLabel");
        loginLabel = (TextField) loader.getNamespace().get("loginLabel");
        passwordLabel = (PasswordField) loader.getNamespace().get("passwordLabel");
        leaderBoardButton = (Button) loader.getNamespace().get("leaderBoardButton");

        authorizationButton.setOnAction(actionEvent -> {
            authorize();
        });

        registerButton.setOnAction(actionEvent -> {
            showRegisterWindow();
        });

        leaderBoardButton.setOnAction(actionEvent -> {
            showLeaderBoardWindow();
        });

        stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void authorize(){
        PlayerSaveModel result = null;
        try{
            result = logic.authorize(loginLabel.getText(), passwordLabel.getText());
        }
        catch (IllegalArgumentException ex){
            errorLabel.setText(ex.getMessage());
            return;
        }

        stage.close();
        Table table = new Table(new LocalSaveLogic(new LocalWriter("save.dat")), loginLabel.getText(), passwordLabel.getText());
        table.setUp(result.getMoney());
    }

    private void showRegisterWindow(){
        stage.close();
        RegistrationController registrationController = new RegistrationController();
        registrationController.setUp();
    }

    private void showLeaderBoardWindow(){
        LeaderBoardController controller = new LeaderBoardController(new LocalReader("save.dat"));
        controller.setUp();
    }
}
