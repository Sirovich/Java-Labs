package poker.logics.registration;

import poker.models.saves.PlayerSaveModel;

public interface RegistrationLogic {
    public int register(String login, String password);
}
