package com.team22.catan.board;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.structures.City;
import com.team22.catan.structures.Road;
import com.team22.catan.structures.Settlement;

public interface Board {
  public List<AxialPosition> getTilePositions();
  public Collection<NodePosition> getNodePositions();
  public Collection<EdgePosition> getEdgePositions();

  public NodePosition getNodePositionFromId(int id);

  public void notifyTilesOfRoll(int roll);

  public boolean canPlaceSettlementAt(NodePosition position, Player player, GameState gameState);
  public void placeSettlementAt(NodePosition position, Settlement settlement, GameState gameState);

  public boolean canPlaceCityAt(NodePosition position, Player player, GameState gameState);
  public void placeCityAt(NodePosition position, City city, GameState gameState);

  public boolean canPlaceRoadAt(EdgePosition position, Player player);
  public void placeRoadAt(EdgePosition position, Road Road);

  Map<NodePosition, Node> getNodes();
  Map<EdgePosition, Edge> getEdges();
  List<Tile> getOrderedTiles();

  List<NodePosition> getNeighborNodes(NodePosition pos);
  boolean isNodeConnectedToPlayerRoad(NodePosition nodePos, com.team22.catan.game.Player player);

  public Tile moveRobberToRandomTile(Random rng);
}