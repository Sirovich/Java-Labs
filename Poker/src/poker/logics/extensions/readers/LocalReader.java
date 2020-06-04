package poker.logics.extensions.readers;

import poker.models.players.Player;
import poker.models.saves.PlayerSaveModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalReader implements Reader {
    private final String path;

    public LocalReader(String path){
        this.path = path;
    }

    @Override
    public List<PlayerSaveModel> readAll() {
        ArrayList<PlayerSaveModel> saves = new ArrayList<PlayerSaveModel>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return saves;
        }

        try(ObjectInputStream input = new ObjectInputStream(inputStream)){
            while(inputStream.available() != 0){
                PlayerSaveModel save = (PlayerSaveModel)input.readObject();
                saves.add(save);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return saves;
        }

        return saves;
    }

}
