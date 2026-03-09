package com.team22.catan.board;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.structures.City;
import com.team22.catan.structures.Road;
import com.team22.catan.structures.Settlement;

public class VisualizerDecorator implements Board {
  private final Board aBoard;
  private final Gson gson;

  public VisualizerDecorator(Board aBoard) {
    this.aBoard = aBoard;
    gson = new GsonBuilder().setPrettyPrinting().create();
    initializeVisualizer();
  }

  private void initializeVisualizer() {
    
  }

  @Override
  public List<AxialPosition> getTilePositions() {
    return aBoard.getTilePositions();
  }

  @Override
  public Collection<NodePosition> getNodePositions() {
    return aBoard.getNodePositions();
  }

  @Override
  public Collection<EdgePosition> getEdgePositions() {
    return aBoard.getEdgePositions();
  }

  @Override
  public NodePosition getNodePositionFromId(int id) {
    return aBoard.getNodePositionFromId(id);
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

  }

  @Override
  public boolean canPlaceCityAt(NodePosition position, Player player, GameState gameState) {
    return aBoard.canPlaceCityAt(position, player, gameState);
  }

  @Override
  public void placeCityAt(NodePosition position, City city, GameState gameState) {
    aBoard.placeCityAt(position, city, gameState);
  }

  @Override
  public boolean canPlaceRoadAt(EdgePosition position, Player player) {
    return aBoard.canPlaceRoadAt(position, player);
  }

  @Override
  public void placeRoadAt(EdgePosition position, Road Road) {
    aBoard.placeRoadAt(position, Road);
  }

  @Override
  public Set<Player> moveRobberToRandomTile(Random rng) {
    return aBoard.moveRobberToRandomTile(rng);
  }
  
  private void updateVisualizer() {
    try (FileWriter jsonWriter = new FileWriter("state.json")) {
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
