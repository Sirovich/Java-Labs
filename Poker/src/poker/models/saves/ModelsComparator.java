package poker.models.saves;

import java.util.Comparator;

public class ModelsComparator implements Comparator<PlayerSaveModel> {
    public int compare(PlayerSaveModel a, PlayerSaveModel b){
        return b.getMoney() - a.getMoney();
    }
}
