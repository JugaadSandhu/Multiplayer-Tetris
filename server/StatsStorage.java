package server;

import java.util.HashMap;

public class StatsStorage {
    HashMap<String, HashMap<String, Integer>> stats;

    public StatsStorage() {
        this.stats = new HashMap<String, HashMap<String, Integer>>();
    }

    /**
     * Make a new entry in stats for a new player.
     */
    public void addPlayer(String playerName) {  //TODO needs to get called by Server before game start
        this.stats.put(playerName, new HashMap<>());
        this.stats.get(playerName).put("rowsCleared", 0);
        this.stats.get(playerName).put("piecesPlaced", 0);
        this.stats.get(playerName).put("points", 0);
    }

    /**
     * Take a string sent by the client and update stats accordingly.
     */
    /*message from client is "playerName:updateStats|rowsCleared|piecesPlaced"*/
    public void updateStats(String message) {
        String[] splitMessage = message.split("\\|");
        this.stats.get(splitMessage[0]).put("rowsCleared", Integer.valueOf(splitMessage[2]));
        this.stats.get(splitMessage[0]).put("points", Integer.valueOf(splitMessage[1]));
        /*
        String[] splitMessage = message.split(":updateStats\\|");
        String playerName = splitMessage[0];
        splitMessage = splitMessage[1].split("\\|");
        this.stats.get(playerName).put("rowsCleared", Integer.valueOf(splitMessage[0]));
        this.stats.get(playerName).put("piecesPlaced", Integer.valueOf(splitMessage[1]));
         */
    }

    /**
     * Update stats of a single player who placed one piece.
     */
    public void placedPiece(String playerName) { //TODO similar for rowsCleared?
        this.stats.get(playerName).put("piecesPlaced", this.stats.get(playerName).get("piecesPlaced") + 1);
    }

    /**
     * Return a string which contains the current stats of the player with no additional message.
     */
    public String getPlayerStats(String playerName) {
        return this.stats.get(playerName).toString();
    }

    /**
     * Return a string which contains the current stats of all players with no additional message.
     */
    public String getAllStats() {
        return this.stats.toString();
    }
}
