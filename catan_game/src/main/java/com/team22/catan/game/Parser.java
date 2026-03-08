package com.team22.catan.game;

import com.team22.catan.actions.*;
import com.team22.catan.board.NodePosition;
import com.team22.catan.board.EdgePosition;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private Scanner scanner;

    private static final Pattern GO_PATTERN = Pattern.compile("^(?i)go$");
    private static final Pattern ROLL_PATTERN = Pattern.compile("^(?i)roll$");
    private static final Pattern LIST_PATTERN = Pattern.compile("^(?i)list$");
    private static final Pattern BUILD_SETTLEMENT_PATTERN = Pattern.compile("^(?i)build\\s+settlement\\s+(\\d+)$");
    private static final Pattern BUILD_CITY_PATTERN = Pattern.compile("^(?i)build\\s+city\\s+(\\d+)$");
    private static final Pattern BUILD_ROAD_PATTERN = Pattern.compile("^(?i)build\\s+road\\s+(\\d+)\\s*,\\s*(\\d+)$");

    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    public void waitForGoCommand() {
        System.out.println("Type 'Go' to start your turn.");
        while (true) {
            String input = scanner.nextLine().trim();
            if (GO_PATTERN.matcher(input).matches()) {
                return;
            }
            System.out.println("Waiting for 'Go' command...");
        }
    }

    public Action parseCommand(Player player, Game game) {
        String input = scanner.nextLine().trim();

        if (LIST_PATTERN.matcher(input).matches()) {
            System.out.println("Inventory: \n" + player.getInventory().toString());
            return null;
        }

        if (ROLL_PATTERN.matcher(input).matches()) {
            System.out.println("Roll command acknowledged.");
            return null;
        }

        Matcher settlementMatcher = BUILD_SETTLEMENT_PATTERN.matcher(input);
        if (settlementMatcher.matches()) {
            int nodeId = Integer.parseInt(settlementMatcher.group(1));
            NodePosition pos = game.getBoard().getNodePositionFromId(nodeId);
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
            NodePosition pos = game.getBoard().getNodePositionFromId(nodeId);
            if (pos != null) {
                return new BuildCity(player, game.getBoard(), pos);
            } else {
                System.out.println("Invalid Node ID.");
                return null;
            }
        }

        Matcher roadMatcher = BUILD_ROAD_PATTERN.matcher(input);
        if (roadMatcher.matches()) {
            int node1Id = Integer.parseInt(roadMatcher.group(1));
            int node2Id = Integer.parseInt(roadMatcher.group(2));

            NodePosition pos1 = game.getBoard().getNodePositionFromId(node1Id);
            NodePosition pos2 = game.getBoard().getNodePositionFromId(node2Id);

            if (pos1 != null && pos2 != null) {
                EdgePosition edgePos = game.getBoard().getEdgePositionFromNodes(pos1, pos2);

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

        if (input.equalsIgnoreCase("end")) {
            return new EndTurn(player, game);
        }

        System.out.println("Invalid command. Please try again.");
        return null;
    }
}