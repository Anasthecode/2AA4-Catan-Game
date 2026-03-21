package com.team22.catan.board;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

/**
 * Applies the Decorator design pattern to the Board interface.
 * This class wraps the CatanBoard and intercepts all structural placement calls
 * (Settlements, Cities, Roads). It exports the game's internal state into
 * a JSON format expected by the external Python visualizer without modifying
 * the
 * underlying board logic, adhering to the Open/Closed Principle.
 */
public class VisualizerDecorator implements Board {
  private enum VisualizerBuildingType {
    SETTLEMENT,
    CITY
  }

  @SuppressWarnings("unused")
  private static class VisualizerBuilding {
    private int node;
    private PlayerColor owner;
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

    public PlayerColor getOwner() {
      return owner;
    }
  }

  private static class VisualizerRoad {
    private int a;
    private int b;
    @SuppressWarnings("unused")
    private PlayerColor owner;

    public VisualizerRoad(int a, int b, PlayerColor owner) {
      this.a = a;
      this.b = b;
      this.owner = owner;
    }

    public int getA() {
      return a;
    }

    public int getB() {
      return b;
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

    public VisualizerBuilding removeBuilding(int node) {
      for (VisualizerBuilding building : buildings) {
        if (building.getNode() == node) {
          buildings.remove(building);
          return building;
        }
      }

      return null;
    }

    public VisualizerRoad removeRoad(int a, int b) {
      for (VisualizerRoad road : roads) {
        if ((road.getA() == a && road.getB() == b) ||
            road.getA() == b && road.getB() == a) {
          roads.remove(road);
          return road;
        }
      }

      return null;
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

  private String statePath = "2aa4-2026-base/assignments/visualize/state.json";
  private String base_mapPath = "2aa4-2026-base/assignments/visualize/base_map.json";

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
      System.out.println(
          "\n[WARNING] Visualizer disabled: Failed to create state JSON. Ensure directory '" + statePath + "' exists.");
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
        System.out.println("\n[WARNING] Visualizer disabled: Failed to create base map JSON. Ensure directory '"
            + base_mapPath + "' exists.");
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
        visualizerFail();
      }

      rewriteState();
    }
  }

  @Override
  public Settlement removeSettlementAt(NodePosition position) {
    if (visualizerEnabled) {
      try (FileReader reader = new FileReader(statePath)) {
        visualizerState = gson.fromJson(reader, VisualizerState.class);
        visualizerState.removeBuilding(getNodePositions().indexOf(position));
      } catch (IOException e) {
        visualizerFail();
      }

      rewriteState();
    }

    return aBoard.removeSettlementAt(position);
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
        int node = getNodePositions().indexOf(position);
        visualizerState.removeBuilding(node);
        visualizerState.addBuilding(new VisualizerBuilding(node,
            city.getOwner().getColor(),
            VisualizerBuildingType.CITY));
      } catch (IOException e) {
        visualizerFail();
      }

      rewriteState();
    }
  }

  @Override
  public City removeCityAt(NodePosition position) {
    if (visualizerEnabled) {
      try (FileReader reader = new FileReader(statePath)) {
        visualizerState = gson.fromJson(reader, VisualizerState.class);
        int node = getNodePositions().indexOf(position);
        VisualizerBuilding oldBuilding = visualizerState.removeBuilding(
            getNodePositions().indexOf(position));

        visualizerState.addBuilding(new VisualizerBuilding(node,
            oldBuilding.getOwner(),
            VisualizerBuildingType.SETTLEMENT));
      } catch (IOException e) {
        visualizerFail();
      }

      rewriteState();
    }

    return aBoard.removeCityAt(position);
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
        visualizerFail();
      }

      rewriteState();
    }
  }

  @Override
  public Road removeRoadAt(EdgePosition position) {
    if (visualizerEnabled) {
      try (FileReader reader = new FileReader(statePath)) {
        visualizerState = gson.fromJson(reader, VisualizerState.class);
        visualizerState.removeRoad(
            getNodePositions().indexOf(position.endpoints().get(0)),
            getNodePositions().indexOf(position.endpoints().get(1)));
      } catch (IOException e) {
        visualizerFail();
      }

      rewriteState();
    }

    return aBoard.removeRoadAt(position);
  }
  
  @Override
  public Player getEdgeOwnerAt(EdgePosition position) {
    return aBoard.getEdgeOwnerAt(position);
  }

  @Override
  public Player getNodeOwnerAt(NodePosition position) {
    return aBoard.getNodeOwnerAt(position);
  }

  @Override
  public Set<Player> moveRobber(AxialPosition position) {
    return aBoard.moveRobber(position);
  }

  @Override
  public AxialPosition getRobberPosition() {
    return aBoard.getRobberPosition();
  }

  @Override
  public TileType[] getTileTypes() {
    return aBoard.getTileTypes();
  }

  @Override
  public int[] getTokens() {
    return aBoard.getTokens();
  }

  @Override
  public int longestRoad(Player player) {
    return aBoard.longestRoad(player);
  }

  private void visualizerFail() {
    System.out.println("\n[WARNING] Visualizer disabled: Failed to find state JSON.");
    visualizerEnabled = false;
  }

  private boolean rewriteState() {
    if (visualizerEnabled) {
      try (FileWriter writer = new FileWriter(statePath, false)) {
        writer.write(gson.toJson(visualizerState));
        return true;
      } catch (IOException e) {
        visualizerFail();
      }
    }

    return false;
  }
}