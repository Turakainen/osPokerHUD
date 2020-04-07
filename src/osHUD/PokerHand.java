package osHUD;

import java.util.ArrayList;
import java.util.List;

public class PokerHand {
    private Player[] players;
    private int buttonIndex;
    private double bigBlindAmount;

    private List<PokerAction> preflop = new ArrayList<PokerAction>();
    private List<PokerAction> flop = new ArrayList<PokerAction>();
    private List<PokerAction> turn = new ArrayList<PokerAction>();
    private List<PokerAction> river = new ArrayList<PokerAction>();

    public PokerHand(Player[] players, int buttonIndex, double bigBlindAmount) {
        this.players = players;
        this.buttonIndex = buttonIndex;
        this.bigBlindAmount = bigBlindAmount;
    }

    public void addPreflopAction(PokerAction action) {
        preflop.add(action);
    }

    public void addFlopAction(PokerAction action) {
        flop.add(action);
    }

    public void addTurnAction(PokerAction action) {
        turn.add(action);
    }

    public void addRiverAction(PokerAction action) {
        river.add(action);
    }
}
