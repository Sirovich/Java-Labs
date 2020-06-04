package poker.logics.authorizations;

import poker.models.saves.PlayerSaveModel;

public interface AuthorizationLogic {
    public PlayerSaveModel authorize(String login, String password);
}
