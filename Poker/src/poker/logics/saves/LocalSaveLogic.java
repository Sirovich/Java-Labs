package poker.logics.saves;

import poker.models.players.Player;
import poker.models.saves.PlayerSaveModel;
import poker.logics.extensions.writers.Writer;

public class LocalSaveLogic implements SaveLogic {
    private Writer writer;

    public LocalSaveLogic(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void save(Player player, String login, String password) {
        PlayerSaveModel saveModel = new PlayerSaveModel(player.getMoney(), login, password);
        writer.write(saveModel);
    }
}
