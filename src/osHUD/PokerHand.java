package osHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokerHand {
    private String id;
    private Map<Integer, String> players = new HashMap<Integer, String>(); 
    private int buttonIndex;
    private double bigBlindAmount;

    private List<PokerAction> preflop = new ArrayList<PokerAction>();
    private List<PokerAction> flop = new ArrayList<PokerAction>();
    private List<PokerAction> turn = new ArrayList<PokerAction>();
    private List<PokerAction> river = new ArrayList<PokerAction>();

    public PokerHand(String id, Map<Integer, String> players, int buttonIndex) {
        this.id = id;
        this.players = players;
        this.buttonIndex = buttonIndex;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Hand #%s\nPlayers: %s\nSeat #%d is on the button.\nPreflop:%s\nFlop:%s\nTurn:%s\nRiver:%s"
                , id, players.toString(), buttonIndex, preflop.toString(), flop.toString(), turn.toString(), river.toString());
    }

    public List<PokerAction> getPreflop() {
        return preflop;
    }

    public void setPreflop(List<PokerAction> preflop) {
        this.preflop = preflop;
    }

    public List<PokerAction> getFlop() {
        return flop;
    }

    public void setFlop(List<PokerAction> flop) {
        this.flop = flop;
    }

    public List<PokerAction> getTurn() {
        return turn;
    }

    public void setTurn(List<PokerAction> turn) {
        this.turn = turn;
    }

    public List<PokerAction> getRiver() {
        return river;
    }

    public void setRiver(List<PokerAction> river) {
        this.river = river;
    }
}
