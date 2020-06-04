package poker;

import javafx.application.Application;
import javafx.stage.Stage;
import poker.controllers.AuthorizationController;
import poker.logics.authorizations.LocalAuthorizationLogic;
import poker.logics.extensions.readers.LocalReader;
import poker.logics.extensions.readers.Reader;
import poker.logics.extensions.writers.LocalWriter;
import poker.logics.extensions.writers.Writer;
import poker.logics.saves.LocalSaveLogic;
import poker.logics.saves.SaveLogic;
import poker.models.Table;
import poker.models.players.Player;
import poker.models.saves.PlayerSaveModel;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AuthorizationController authorization = new AuthorizationController(new LocalAuthorizationLogic(new LocalReader("save.dat")));
       authorization.setUp();
        //Table table = new Table(new LocalSaveLogic, "", "");
        //table.setUp();
    }


    public static void main(String[] args) {
        launch(args);
    }
}