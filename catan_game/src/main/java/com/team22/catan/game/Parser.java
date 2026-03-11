package com.team22.catan.game;

import com.team22.catan.actions.*;
import com.team22.catan.board.NodePosition;
import com.team22.catan.board.EdgePosition;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles all Command Line Interface (CLI) input from the human player.
 * Uses Regular Expressions (Regex) to securely parse user strings and map them
 * to executable Action objects (Command Pattern).
 */
public class Parser {
    private Scanner scanner;

    private static final Pattern GO_PATTERN = Pattern.compile("^(?i)go$");
    private static final Pattern ROLL_PATTERN = Pattern.compile("^(?i)roll$");
    private static final Pattern LIST_PATTERN = Pattern.compile("^(?i)list$");
    private static final Pattern BUILD_SETTLEMENT_PATTERN = Pattern.compile("^(?i)build\\s+settlement\\s+(\\d+)$");
    private static final Pattern BUILD_CITY_PATTERN = Pattern.compile("^(?i)build\\s+city\\s+(\\d+)$");
    private static final Pattern BUILD_ROAD_PATTERN = Pattern.compile("^(?i)build\\s+road\\s+(\\d+)\\s*,\\s*(\\d+)$");
    private static final Pattern TRADE_PATTERN = Pattern.compile("^(?i)trade\\s+(\\d+)\\s+(\\w+)\\s+for\\s+(\\d+)\\s+(\\w+)$");

    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    public void waitForGoCommand() {
        System.out.println("Type 'Go' step to the next turn.");
        while (true) {
            String input = scanner.nextLine().trim();
            if (GO_PATTERN.matcher(input).matches()) {
                return;
            }
            System.out.println("Waiting for 'Go' command...");
        }
    }

    public Action parseCommandFromString(Player player, Game game, String input) {
        if (LIST_PATTERN.matcher(input).matches()) {
            return new DisplayInventory(player);
        }

        if (ROLL_PATTERN.matcher(input).matches()) {
            return new GenerateResources(player, game);
        }

        Matcher settlementMatcher = BUILD_SETTLEMENT_PATTERN.matcher(input);
        if (settlementMatcher.matches()) {
            int nodeId = Integer.parseInt(settlementMatcher.group(1));
            NodePosition pos = null;
            if (nodeId < game.getBoard().getNodePositions().size()) {
                pos = game.getBoard().getNodePositions().get(nodeId);
            }

            if (pos != null) {
                return new BuildSettlement(player, game, pos);
            } else {
                System.out.println("Invalid Node ID.");
                return null;
            }
        }

        Matcher cityMatcher = BUILD_CITY_PATTERN.matcher(input);
        if (cityMatcher.matches()) {
            int nodeId = Integer.parseInt(cityMatcher.group(1));
            NodePosition pos = null;
            if (nodeId < game.getBoard().getNodePositions().size()) {
                pos = game.getBoard().getNodePositions().get(nodeId);
            }
            
            if (pos != null) {
                return new BuildCity(player, game, pos);
            } else {
                System.out.println("Invalid Node ID.");
                return null;
            }
        }

        Matcher roadMatcher = BUILD_ROAD_PATTERN.matcher(input);
        if (roadMatcher.matches()) {
            int node1Id = Integer.parseInt(roadMatcher.group(1));
            int node2Id = Integer.parseInt(roadMatcher.group(2));

            NodePosition pos1 = null;
            NodePosition pos2 = null;

            int nodesSize = game.getBoard().getNodePositions().size();
            if (node1Id < nodesSize && node2Id < nodesSize) {
                pos1 = game.getBoard().getNodePositions().get(node1Id);
                pos2 = game.getBoard().getNodePositions().get(node2Id);
            }

            if (pos1 != null && pos2 != null) {

                EdgePosition edgePos = null;
                for (EdgePosition edgePosition : game.getBoard().getEdgePositions()) {
                    if (edgePosition.endpoints().contains(pos1) && edgePosition.endpoints().contains(pos2)) {
                        edgePos = edgePosition;
                    }
                }

                if (edgePos != null) {
                    return new BuildRoad(player, game, edgePos);
                } else {
                    System.out.println("Invalid road placement: Those nodes are not adjacent.");
                    return null;
                }
            } else {
                System.out.println("Invalid Node ID entered.");
                return null;
            }
        }

        if (GO_PATTERN.matcher(input).matches()) {
            return new EndTurn(player, game);
        }

        System.out.println("Invalid command. Please try again.");
        return null;
    }
    
    public Action parseCommand(Player player, Game game) {
        String input = scanner.nextLine().trim();
        return parseCommandFromString(player, game, input);
    }
}