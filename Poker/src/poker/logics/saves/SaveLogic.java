package poker.logics.saves;

import poker.models.players.Player;

public interface SaveLogic {
    public void save(Player player, String login, String password);
}
