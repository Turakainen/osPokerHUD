package osHUD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import osHUD.PokerAction.Type;

public class HandHistoryParser {

    /**
     * Parses hand history file into Hand objects
     * @param filepath location for hand history
     * @return list of pokerHand objects
     */
    public static List<PokerHand> parseFile(String filepath) {
        List<PokerHand> hands = new ArrayList<PokerHand>();
        Scanner fi;
        fi = openHistoryFile();
        if (fi == null) return null;

        String singleHand = "";
        try {
            while ( fi.hasNextLine() ) {
                String s = fi.nextLine();
                singleHand = s.startsWith("PokerStars Hand")
                        ? s : singleHand + "\n" + s;
                if(s.isBlank() && !singleHand.isBlank()) {
                    hands.add(parseHand(singleHand));
                    singleHand = "";
                }
            }
        } finally {
            fi.close();
        }
        
        return hands;
    }

    /**
     *
     * @param hand
     */
    private static PokerHand parseHand(String hand) {
        String handId = getHandId(hand);
        Map<Integer, String> players = getPlayers(hand);
        int button = getButton(hand);

        List<PokerAction> preflop = getPokerActions(
                getStringBetween(hand,"*** HOLE CARDS ***", "*** FLOP ***", "*** SUMMARY ***"));
        List<PokerAction> flop = getPokerActions(
                getStringBetween(hand,"*** FLOP ***", "*** TURN ***", "*** SUMMARY ***"));
        List<PokerAction> turn = getPokerActions(
                getStringBetween(hand,"*** TURN ***", "*** RIVER ***", "*** SUMMARY ***"));
        List<PokerAction> river = getPokerActions(
                getStringBetween(hand,"*** RIVER ***", "*** SHOW DOWN ***", "*** SUMMARY ***"));
        
        PokerHand pokerHand = new PokerHand(handId, players, button);
        pokerHand.setPreflop(preflop);
        pokerHand.setFlop(flop);
        pokerHand.setTurn(turn);
        pokerHand.setRiver(river);
        System.out.println(pokerHand.toString());
        
        return pokerHand;
    }

    private static String getStringBetween(String text, String start, String end, String optEnd) {
        int start1 = text.indexOf(start);
        int end1 = text.indexOf(end);
        int end2 = text.indexOf(optEnd);
        
        if(start1 != -1 && end1 != -1)
            return text.substring(start1 + start.length(), end1);
        if(start1 != -1 && end2 != -1)
            return text.substring(start1 + start.length(), end2);
        return "";
    }

    private static List<PokerAction> getPokerActions(String handHistory) {
        List<PokerAction> actions = new ArrayList<PokerAction>();
        Scanner fi = new Scanner(handHistory);
        while(fi.hasNextLine()) {
            String s = fi.nextLine();
            String[] params = s.split(" ");
            if(params.length >= 2 && !params[0].equals("Dealt")) {
                PokerAction action = parseAction(params);
                if(action != null) actions.add(action);
            }
        }
        return actions;
    }

    private static PokerAction parseAction(String[] params) {
        PokerAction action;
        switch (params[1]) {
            case "checks":
                action = new PokerAction(Type.Check);
                action.setPlayer(params[0].replace(":", ""));
                return action;
            case "calls":
                action = new PokerAction(Type.Call);
                action.setPlayer(params[0].replace(":", ""));
                return action;
            case "bets":
                action = new PokerAction(Type.Bet);
                action.setPlayer(params[0].replace(":", ""));
                return action;
            case "raises":
                action = new PokerAction(Type.Raise);
                action.setPlayer(params[0].replace(":", ""));
                return action;
            case "folds":
                action = new PokerAction(Type.Fold);
                action.setPlayer(params[0].replace(":", ""));
                return action;
            case "leaves":
                action = new PokerAction(Type.Leave);
                action.setPlayer(params[0].replace(":", ""));
                return action;
            default:
                return null;
        }
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
        } 
        System.out.println("Error: Cannot find hand button location!");
        return "";
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
        }
        System.out.println("Error: Cannot find hand button location!");
        return -1;
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
