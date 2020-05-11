package osHUD;

public class Player {

    private String name;
    private int handsPlayed;
    private int preflopCalls;
    private int preflopBets;
    private int preflopRaises;

    /**
     * @param name the player name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * @return the vpip rate
     */
    public double getVPIP() {
        return (preflopCalls + preflopBets + preflopRaises) / (double)handsPlayed * 100;
    }

    /**
     * @return the pfr rate
     */
    public double getPfr() {
        return (preflopBets + preflopRaises) / (double)handsPlayed * 100;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the handsPlayed
     */
    public int getHandsPlayed() {
        return handsPlayed;
    }

    /**
     * @return the preflopCalls
     */
    public int getPreflopCalls() {
        return preflopCalls;
    }

    /**
     * @return the preflopBets
     */
    public int getPreflopBets() {
        return preflopBets;
    }

    /**
     * @return the preflopRaises
     */
    public int getPreflopRaises() {
        return preflopRaises;
    }

    /**
     * @param handsPlayed the handsPlayed to set
     */
    public void setHandsPlayed(int handsPlayed) {
        this.handsPlayed = handsPlayed;
    }

    /**
     * @param preflopCalls the preflopCalls to set
     */
    public void setPreflopCalls(int preflopCalls) {
        this.preflopCalls = preflopCalls;
    }

    /**
     * @param preflopBets the preflopBets to set
     */
    public void setPreflopBets(int preflopBets) {
        this.preflopBets = preflopBets;
    }

    /**
     * @param preflopRaises the preflopRaises to set
     */
    public void setPreflopRaises(int preflopRaises) {
        this.preflopRaises = preflopRaises;
    }
}

