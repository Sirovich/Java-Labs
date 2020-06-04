package poker.logics.extensions.writers;

import poker.models.saves.PlayerSaveModel;

import java.util.List;

public interface Writer {
    public void write(PlayerSaveModel saveModel);

    public void write(List<PlayerSaveModel> saveModelList);
}
