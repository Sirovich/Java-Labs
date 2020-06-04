package poker.logics.registration;

import poker.logics.extensions.writers.Writer;
import poker.models.saves.PlayerSaveModel;

public class LocalRegistrationLogic implements RegistrationLogic{

    private final Writer writer;

    public LocalRegistrationLogic(Writer writer){
        this.writer = writer;
    }

    @Override
    public int register(String login, String password) {
        if (login == null || login.length() == 0){
            return 0;
        }

        if (password == null || password.length() == 0){
            return -1;
        }

        PlayerSaveModel saveModel = new PlayerSaveModel(2500, login, password);
        writer.write(saveModel);
        return 1;
    }
}
