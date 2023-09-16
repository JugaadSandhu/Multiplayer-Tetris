package server;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class StatsStorageTests {
    @Test
    void testAddPlayer() {
        StatsStorage output = new StatsStorage();
        output.addPlayer("Player1");
        output.addPlayer("Player2");
        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("rowsCleared", 0);
        stats.put("piecesPlaced", 0);
        stats.put("points", 0);
        HashMap<String, HashMap<String, Integer>> target = new HashMap<String, HashMap<String, Integer>>();
        target.put("Player1", (HashMap<String, Integer>) stats.clone());
        target.put("Player2", (HashMap<String, Integer>) stats.clone());
        assertEquals(output.stats, target, "Error when testing addPlayer.");
    }

    @Test
    void testUpdateStats() {
        StatsStorage output = new StatsStorage();
        output.addPlayer("Player1");
        output.addPlayer("Player2");
        output.updateStats("Player2|25|3");
        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("rowsCleared", 0);
        stats.put("piecesPlaced", 0);
        stats.put("points", 0);
        HashMap<String, HashMap<String, Integer>> target = new HashMap<String, HashMap<String, Integer>>();
        target.put("Player1", (HashMap<String, Integer>) stats.clone());
        stats.put("rowsCleared", 3);
        stats.put("piecesPlaced", 0);
        stats.put("points", 25);
        target.put("Player2", (HashMap<String, Integer>) stats.clone());
        assertEquals(output.stats, target, "Error when testing updateStats.");
    }

    @Test
    void testPlacedPiece() {
        StatsStorage output = new StatsStorage();
        output.addPlayer("Player1");
        output.addPlayer("Player2");
        output.placedPiece("Player1");
        output.placedPiece("Player1");
        output.placedPiece("Player2");
        output.placedPiece("Player2");
        output.placedPiece("Player2");
        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("rowsCleared", 0);
        stats.put("piecesPlaced", 2);
        stats.put("points", 0);
        HashMap<String, HashMap<String, Integer>> target = new HashMap<String, HashMap<String, Integer>>();
        target.put("Player1", (HashMap<String, Integer>) stats.clone());
        stats.put("rowsCleared", 0);
        stats.put("piecesPlaced", 3);
        stats.put("points", 0);
        target.put("Player2", (HashMap<String, Integer>) stats.clone());
        assertEquals(output.stats, target, "Error when testing placedPiece.");
    }
}
