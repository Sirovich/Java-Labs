package poker.logics.extensions.writers;

import poker.logics.extensions.readers.LocalReader;
import poker.logics.extensions.readers.Reader;
import poker.models.saves.PlayerSaveModel;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class LocalWriter implements Writer {
    private final String path;
    private final Reader reader;

    public LocalWriter(String path){
        this.path = path;
        reader = new LocalReader(path);
    }

    @Override
    public void write(PlayerSaveModel saveModel) {
        boolean isUnique = true;

        List<PlayerSaveModel> saves = reader.readAll();
        for(int i = 0; i < saves.size(); i++){
            if (saves.get(i).getLogin().equals(saveModel.getLogin())){
                saves.set(i, saveModel);
                isUnique = false;
            }
        }
        if (isUnique){
            saves.add(saveModel);
        }
        write(saves);
    }

    @Override
    public void write(List<PlayerSaveModel> saveModelList) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat")))
        {
            for(PlayerSaveModel save: saveModelList){
                oos.writeObject(save);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
