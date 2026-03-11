package com.team22.catan.board;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.game.Player.PlayerColor;
import com.team22.catan.game.Resource;
import com.team22.catan.structures.City;
import com.team22.catan.structures.Road;
import com.team22.catan.structures.Settlement;

public class VisualizerDecorator implements Board {
    private enum VisualizerBuildingType {
        SETTLEMENT,
        CITY
    }

    private static class VisualizerBuilding {
        @SuppressWarnings("unused")
        private int node;
        @SuppressWarnings("unused")
        private PlayerColor owner;
        @SuppressWarnings("unused")
        private VisualizerBuildingType type;

        public VisualizerBuilding(
                int node, PlayerColor owner, VisualizerBuildingType type) {

            this.node = node;
            this.owner = owner;
            this.type = type;
        }

        public int getNode() {
            return node;
        }
    }

    private static class VisualizerRoad {
        @SuppressWarnings("unused")
        private int a;
        @SuppressWarnings("unused")
        private int b;
        @SuppressWarnings("unused")
        private PlayerColor owner;

        public VisualizerRoad(int a, int b, PlayerColor owner) {
            this.a = a;
            this.b = b;
            this.owner = owner;
        }
    }

    private static class VisualizerState {
        private List<VisualizerRoad> roads;
        private List<VisualizerBuilding> buildings;

        public VisualizerState() {
            buildings = new ArrayList<>();
            roads = new ArrayList<>();
        }

        public void addBuilding(VisualizerBuilding building) {
            buildings.add(building);
        }

        public void addRoad(VisualizerRoad road) {
            roads.add(road);
        }

        public List<VisualizerBuilding> getBuildings() {
            return buildings;
        }
    }

    private static class VisualizerTile {
        @SuppressWarnings("unused")
        private int q;
        @SuppressWarnings("unused")
        private int s;
        @SuppressWarnings("unused")
        private int r;

        @SuppressWarnings("unused")
        private Resource resource;

        @SuppressWarnings("unused")
        private Integer number;

        public VisualizerTile(int q, int r, Resource resource, Integer number) {
            this.q = q;
            this.r = r;
            this.s = -q - r;

            this.resource = resource;

            if (number == 0) {
                this.number = null;
            } else {
                this.number = number;
            }
        }
    }

    private static class VisualizerBoard {
        private List<VisualizerTile> tiles;

        public VisualizerBoard() {
            tiles = new ArrayList<>();
        }

        public void addTile(VisualizerTile tile) {
            tiles.add(tile);
        }
    }

    private final Board aBoard;
    private final Gson gson;
    private VisualizerState visualizerState;

    private String statePath = "../assignments/visualize/TestingState.json";
    private String base_mapPath = "../assignments/visualize/base_map.json";

    private boolean visualizerEnabled = true;

    public VisualizerDecorator(Board aBoard) {
        this.aBoard = aBoard;
        gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        visualizerState = new VisualizerState();
        initializeVisualizer();
    }

    private void initializeVisualizer() {
        try (FileWriter writer = new FileWriter(statePath, false)) {
            writer.write(gson.toJson(visualizerState));
        } catch (IOException e) {
            System.out.println("\n[WARNING] Visualizer disabled: Failed to create state JSON. Ensure directory '" + statePath + "' exists.");
            visualizerEnabled = false;
        }

        if (visualizerEnabled) {
            try (FileWriter writer = new FileWriter(base_mapPath, false)) {
                List<AxialPosition> positions = getTilePositions();
                TileType[] tileTypes = getTileTypes();
                int[] tokens = getTokens();

                VisualizerBoard visualizerBoard = new VisualizerBoard();

                for (int i = 0; i < positions.size(); i++) {
                    VisualizerTile newTile = new VisualizerTile(
                            positions.get(i).getQ(), positions.get(i).getR(),
                            tileTypes[i].getProducedResource(), tokens[i]);

                    visualizerBoard.addTile(newTile);
                }

                writer.write(gson.toJson(visualizerBoard));
            } catch (IOException e) {
                System.out.println("\n[WARNING] Visualizer disabled: Failed to create base map JSON. Ensure directory '" + base_mapPath + "' exists.");
                visualizerEnabled = false;
            }
        }
    }

    @Override
    public List<AxialPosition> getTilePositions() {
        return aBoard.getTilePositions();
    }

    @Override
    public List<NodePosition> getNodePositions() {
        return aBoard.getNodePositions();
    }

    @Override
    public Collection<EdgePosition> getEdgePositions() {
        return aBoard.getEdgePositions();
    }

    @Override
    public void notifyTilesOfRoll(int roll) {
        aBoard.notifyTilesOfRoll(roll);
    }

    @Override
    public boolean canPlaceSettlementAt(NodePosition position, Player player, GameState gameState) {
        return aBoard.canPlaceSettlementAt(position, player, gameState);
    }

    @Override
    public void placeSettlementAt(NodePosition position, Settlement settlement, GameState gameState) {
        aBoard.placeSettlementAt(position, settlement, gameState);

        if (visualizerEnabled) {
            try (FileReader reader = new FileReader(statePath)) {
                visualizerState = gson.fromJson(reader, VisualizerState.class);
                visualizerState.addBuilding(new VisualizerBuilding(
                        getNodePositions().indexOf((position)),
                        settlement.getOwner().getColor(),
                        VisualizerBuildingType.SETTLEMENT));
            } catch (IOException e) {
                System.out.println("\n[WARNING] Visualizer disabled: Failed to read state JSON.");
                visualizerEnabled = false;
            }

            if (visualizerEnabled) {
                try (FileWriter writer = new FileWriter(statePath, false)) {
                    writer.write(gson.toJson(visualizerState));
                } catch (IOException e) {
                    System.out.println("\n[WARNING] Visualizer disabled: Failed to write state JSON.");
                    visualizerEnabled = false;
                }
            }
        }
    }

    @Override
    public boolean canPlaceCityAt(NodePosition position, Player player, GameState gameState) {
        return aBoard.canPlaceCityAt(position, player, gameState);
    }

    @Override
    public void placeCityAt(NodePosition position, City city, GameState gameState) {
        aBoard.placeCityAt(position, city, gameState);

        if (visualizerEnabled) {
            try (FileReader reader = new FileReader(statePath)) {
                visualizerState = gson.fromJson(reader, VisualizerState.class);
                for (VisualizerBuilding building : visualizerState.getBuildings()) {
                    if (building.getNode() == getNodePositions().indexOf(position)) {
                        visualizerState.getBuildings().remove(building);
                        break;
                    }
                }

                visualizerState.addBuilding(new VisualizerBuilding(
                        getNodePositions().indexOf(position),
                        city.getOwner().getColor(),
                        VisualizerBuildingType.CITY));
            } catch (IOException e) {
                System.out.println("\n[WARNING] Visualizer disabled: Failed to read state JSON.");
                visualizerEnabled = false;
            }

            if (visualizerEnabled) {
                try (FileWriter writer = new FileWriter(statePath, false)) {
                    writer.write(gson.toJson(visualizerState));
                } catch (IOException e) {
                    System.out.println("\n[WARNING] Visualizer disabled: Failed to write state JSON.");
                    visualizerEnabled = false;
                }
            }
        }
    }

    @Override
    public boolean canPlaceRoadAt(EdgePosition position, Player player, GameState gameState) {
        return aBoard.canPlaceRoadAt(position, player, gameState);
    }

    @Override
    public void placeRoadAt(EdgePosition position, Road road, GameState gameState) {
        aBoard.placeRoadAt(position, road, gameState);

        if (visualizerEnabled) {
            try (FileReader reader = new FileReader(statePath)) {
                visualizerState = gson.fromJson(reader, VisualizerState.class);
                visualizerState.addRoad(new VisualizerRoad(
                        getNodePositions().indexOf(position.endpoints().get(0)),
                        getNodePositions().indexOf(position.endpoints().get(1)),
                        road.getOwner().getColor()));
            } catch (IOException e) {
                System.out.println("\n[WARNING] Visualizer disabled: Failed to read state JSON.");
                visualizerEnabled = false;
            }

            if (visualizerEnabled) {
                try (FileWriter writer = new FileWriter(statePath, false)) {
                    writer.write(gson.toJson(visualizerState));
                } catch (IOException e) {
                    System.out.println("\n[WARNING] Visualizer disabled: Failed to write state JSON.");
                    visualizerEnabled = false;
                }
            }
        }
    }

    @Override
    public Set<Player> moveRobberToRandomTile(Random rng) {
        return aBoard.moveRobberToRandomTile(rng);
    }

    @Override
    public TileType[] getTileTypes() {
        return aBoard.getTileTypes();
    }

    @Override
    public int[] getTokens() {
        return aBoard.getTokens();
    }
}