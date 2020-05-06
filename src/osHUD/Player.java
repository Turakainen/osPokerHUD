package osHUD;

public class Player {

    private String name;
    private int handsPlayed;
    private int preflopCalls;
    private int preflopBets;
    private int preflopRaises;

    public Player(String name) {
        this.name = name;
    }

    public double getVPIP() {
        return (preflopCalls + preflopBets + preflopRaises) / (double)handsPlayed * 100;
    }

    public double getPfr() {
        return (preflopBets + preflopRaises) / (double)handsPlayed * 100;
    }

    public String getName() {
        return name;
    }

    public int getHandsPlayed() {
        return handsPlayed;
    }


    public int getPreflopCalls() {
        return preflopCalls;
    }

    public int getPreflopBets() {
        return preflopBets;
    }

    public int getPreflopRaises() {
        return preflopRaises;
    }
}

