package poker.logics.authorizations;

import poker.logics.extensions.readers.Reader;
import poker.logics.extensions.writers.Writer;
import poker.models.players.Player;
import poker.models.saves.PlayerSaveModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.List;

public class LocalAuthorizationLogic implements AuthorizationLogic {
    private final Reader reader;

    public LocalAuthorizationLogic(Reader reader){
        this.reader = reader;
    }

    @Override
    public PlayerSaveModel authorize(String login, String password) throws IllegalArgumentException{
        if (login == null || login.length() == 0){
            throw new IllegalArgumentException("Wrong login");
        }

        if (password == null || password.length() == 0){
            throw new IllegalArgumentException("Wrong password");
        }

        List<PlayerSaveModel> saves = reader.readAll();
        for(PlayerSaveModel save: saves){
            if (save.getLogin().equals(login)){
                if (save.getPassword().equals(password)){
                    return save;
                }
                else{
                    throw new IllegalArgumentException("Wrong password");
                }
            }
        }

        throw new IllegalArgumentException("Wrong login");
    }
}
