package com.team22.catan.board;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.structures.City;
import com.team22.catan.structures.Road;
import com.team22.catan.structures.Settlement;

public interface Board {
  public List<AxialPosition> getTilePositions();
  public List<NodePosition> getNodePositions();
  public Collection<EdgePosition> getEdgePositions();
  public TileType[] getTileTypes();
  public int[] getTokens();

  public void notifyTilesOfRoll(int roll);

  public boolean canPlaceSettlementAt(NodePosition position, Player player, GameState gameState);
  public void placeSettlementAt(NodePosition position, Settlement settlement, GameState gameState);
  public Settlement removeSettlementAt(NodePosition position);

  public boolean canPlaceCityAt(NodePosition position, Player player, GameState gameState);
  public void placeCityAt(NodePosition position, City city, GameState gameState);
  public City removeCityAt(NodePosition position);

  public boolean canPlaceRoadAt(EdgePosition position, Player player, GameState gameState);
  public void placeRoadAt(EdgePosition position, Road Road, GameState gameState);
  public Road removeRoadAt(EdgePosition position);

  public Set<Player> moveRobber(AxialPosition position);
  public AxialPosition getRobberPosition();

  public int longestRoad(Player player);
}