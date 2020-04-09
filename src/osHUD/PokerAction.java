package osHUD;

public class PokerAction {
    
    public enum Type { Bet, Call, Raise, Fold, Check, Leave }
    
    private String player;
    private Type type = null;
    
    public PokerAction(Type type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return player + ": " +type.toString();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
