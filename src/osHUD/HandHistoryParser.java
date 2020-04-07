package osHUD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandHistoryParser {

    /**
     * Parses hand history file into Hand objects
     * @param filepath location for hand history
     * @return
     */
    public static List<PokerHand> parseFile(String filepath) {
        Scanner fi;
        fi = openHistoryFile();
        if (fi == null) return null;

        String singleHand = "";
        try {
            while ( fi.hasNextLine() ) {
                String s = fi.nextLine();
                singleHand = s.startsWith("PokerStars Hand") && !s.isBlank()
                        ? s : singleHand + "\n" + s;
                if(s.startsWith("*** SUMMARY ***")) {
                    parseHand(singleHand);
                    singleHand = "";
                }
            }
        } finally {
            fi.close();
        }
        return null;
    }

    /**
     *
     * @param hand
     */
    private static void parseHand(String hand) {
        String handId = getHandId(hand);
        Map<Integer, String> players = getPlayers(hand);
        int button = getButton(hand);

        System.out.println("Hand #" + handId);
        System.out.println(players.toString());
        System.out.printf("Seat #%d is the button\n", button);

        List<PokerAction> preflop = getPokerActions(getStringBetween(hand,"*** HOLE CARDS ***", "*** FLOP ***"));
        List<PokerAction> flop = getPokerActions(getStringBetween(hand,"*** FLOP ***", "*** TURN ***"));
        List<PokerAction> turn = getPokerActions(getStringBetween(hand,"*** TURN ***", "*** RIVER ***"));
        List<PokerAction> river = getPokerActions(getStringBetween(hand,"*** RIVER ***", "*** SHOW DOWN ***"));
    }

    private static String getStringBetween(String text, String start, String end, String optEnd) {
        Scanner fi = new Scanner(text);
        String target = "";
        try {
            while ( fi.hasNextLine() ) {
                String s = fi.nextLine();
                if(s.startsWith(start)) {
                    target = "";
                } else {
                    if(s.startsWith(end) || s.startsWith(optEnd)) {
                        return target.trim();
                    }
                    target += s + "\n";
                }
            }
        } finally {
            fi.close();
        }
        return "";
    }

    private static List<PokerAction> getPokerActions(String handHistory) {
        System.out.println("===");
        System.out.println(handHistory);
        System.out.println("===");
        return null;
    }

    /**
     * Get hand id from hand
     * @param hand poker hand as a String
     * @return hand id
     */
    private static String getHandId(String hand) {
        Pattern pattern = Pattern.compile("PokerStars Hand #(\\d+)");
        Matcher matcher = pattern.matcher(hand);
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("Error: Cannot find hand button location!");
            return "";
        }
    }

    /**
     * Get button location as seat number from hand
     * @param hand poker hand as a String
     * @return button location
     */
    private static int getButton(String hand) {
        Pattern pattern = Pattern.compile("Seat #(\\d+) is the button");
        Matcher matcher = pattern.matcher(hand);
        if(matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            System.out.println("Error: Cannot find hand button location!");
            return -1;
        }
    }

    /**
     * Parses player names and seats from hand history String
     * @param hand poker hand as a String
     * @return map with seats as keys and player names as values
     */
    private static Map<Integer, String> getPlayers(String hand) {
        Map<Integer, String> playerMap = new HashMap<>();
        Pattern pattern = Pattern.compile("Seat (\\d+): (\\S+)");
        Matcher matcher = pattern.matcher(hand);
        while(matcher.find()) {
            playerMap.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
        }
        return playerMap;
    }

    /**
     * Reads PokerStars hand history file
     * @return Scanner with hand history
     */
    private static Scanner openHistoryFile() {
        Scanner fi;
        try {
            fi = new Scanner(new FileInputStream(new File("PokerStarsHandHistory.txt")));
        } catch (FileNotFoundException ex) {
            System.err.println("File cannot be opened! "+ex.getMessage());
            return null;
        }
        return fi;
    }

    /**
     * Test class
     * @param args Not in use
     */
    public static void main(String[] args) {
        System.out.println("Parsing history file...");
        parseFile("");
    }
}
