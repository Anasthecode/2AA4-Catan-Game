package com.team22.catan.game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.team22.catan.board.*;
import com.team22.catan.structures.*;

public class StateExporter {

    private static final String FILE_PATH = "state.json";

    /**
     * Maps the player's list index to the color names the visualizer requires.
     */
    private static String getPlayerColor(int index) {
        switch (index) {
            case 0: return "RED";
            case 1: return "BLUE";
            case 2: return "WHITE";
            case 3: return "ORANGE";
            default: return "GRAY";
        }
    }

    public static void exportGameState(Game game) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");

        json.append("  \"buildings\": [\n");
        boolean firstBuilding = true;
        for (int id = 0; id <= 53; id++) {
            NodePosition pos = game.getBoard().getNodePositionFromId(id);
            Node node = game.getBoard().getNodes().get(pos);

            if (node != null && node.hasStructure()) {
                if (!firstBuilding) json.append(",\n");

                int playerIdx = game.getPlayers().indexOf(node.getStructureObject().getOwner());
                String type = (node.getStructureObject() instanceof City) ? "CITY" : "SETTLEMENT";

                json.append("    {")
                        .append("\"node\": ").append(id).append(", ")
                        .append("\"owner\": \"").append(getPlayerColor(playerIdx)).append("\", ")
                        .append("\"type\": \"").append(type).append("\"")
                        .append("}");
                firstBuilding = false;
            }
        }
        json.append("\n  ],\n");

        json.append("  \"roads\": [\n");
        boolean firstRoad = true;
        for (Edge edge : game.getBoard().getEdges().values()) {
            if (edge.hasRoad()) {
                if (!firstRoad) json.append(",\n");

                int playerIdx = game.getPlayers().indexOf(edge.getRoad().getOwner());
                int nodeA = findNodeId(game.getBoard(), edge.endpoints().get(0));
                int nodeB = findNodeId(game.getBoard(), edge.endpoints().get(1));

                json.append("    {")
                        .append("\"a\": ").append(nodeA).append(", ")
                        .append("\"b\": ").append(nodeB).append(", ")
                        .append("\"owner\": \"").append(getPlayerColor(playerIdx)).append("\"")
                        .append("}");
                firstRoad = false;
            }
        }
        json.append("\n  ],\n");

        json.append("  \"robber\": ");
        int robberTileIdx = 0;
        List<Tile> tiles = game.getBoard().getOrderedTiles();
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).hasRobber()) {
                robberTileIdx = i;
                break;
            }
        }
        json.append(robberTileIdx).append("\n");

        json.append("}");

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(json.toString());
        } catch (IOException e) {
            System.err.println("Failed to export game state: " + e.getMessage());
        }
    }

    /**
     * Helper to resolve a NodePosition back to its integer ID (0-53).
     */
    private static int findNodeId(Board board, NodePosition pos) {
        for (int i = 0; i <= 53; i++) {
            if (board.getNodePositionFromId(i).equals(pos)) {
                return i;
            }
        }
        return -1;
    }
}