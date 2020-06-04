package poker.logics.extensions.readers;

import poker.models.saves.PlayerSaveModel;

import java.util.List;

public interface Reader {
    public List<PlayerSaveModel> readAll();
}
