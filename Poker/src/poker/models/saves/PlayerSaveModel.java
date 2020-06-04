package poker.models.saves;

import java.io.Serializable;
import java.util.Comparator;

public class PlayerSaveModel implements Serializable {
    private String login;
    private String password;
    private int money;

    public PlayerSaveModel(int money, String login, String password){
        this.money = money;
        this.login = login;
        this.password = password;
    }

    public int getMoney(){
        return money;
    }

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }
}
