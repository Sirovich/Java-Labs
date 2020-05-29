package poker.models.enums;

public enum GameStage {
    flop(1),
    turn(2),
    river(3),
    end(4);

    private final int gameStage;

    GameStage(final int gameStage) {
        this.gameStage = gameStage;
    }

    public int getGameStage() {
        return this.gameStage;
    }
}
