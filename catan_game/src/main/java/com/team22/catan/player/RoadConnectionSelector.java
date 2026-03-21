package com.team22.catan.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.board.Board;
import com.team22.catan.board.EdgePosition;
import com.team22.catan.board.NodePosition;
import com.team22.catan.game.Game;
import com.team22.catan.game.Player;

public class RoadConnectionSelector extends ActionSelector {

  @Override
  public Action chooseAction(Game game, Player player, List<Action> availableActions) {
    Board board = game.getBoard();

    Map<EdgePosition, Action> buildRoadActions = new HashMap<>();
    for (Action action : availableActions) {
      if (action instanceof BuildRoad) {
        BuildRoad br = (BuildRoad) action;
        buildRoadActions.put(br.roadPlacementPosition(), action);
      }
    }

    for (EdgePosition edgePosition : board.getEdgePositions()) {
      if (!player.equals(board.getEdgeOwnerAt(edgePosition))) {
        continue;
      }

      List<EdgePosition> adjacentEdges = getAdjacentEdges(edgePosition, board);
      for (EdgePosition adjacentEdge : adjacentEdges) {
        for (EdgePosition secondaryAdjacentEdge : getAdjacentEdges(adjacentEdge, board)) {
          if (secondaryAdjacentEdge.equals(edgePosition) ||
              adjacentEdges.contains(secondaryAdjacentEdge) ||
              !player.equals(board.getEdgeOwnerAt(secondaryAdjacentEdge))) {
            continue;
          }

          if (buildRoadActions.containsKey(adjacentEdge) &&
              adjacentEdge.connectsTo(edgePosition) &&
              adjacentEdge.connectsTo(secondaryAdjacentEdge)) {
            return buildRoadActions.get(adjacentEdge);
          }
        }
      }
    }

    if (nextHandler != null) {
      return nextHandler.chooseAction(game, player, availableActions);
    }

    return null;
  }

  private List<EdgePosition> getAdjacentEdges(EdgePosition edgePosition, Board board) {
    List<EdgePosition> adjacentEdges = new ArrayList<>();
    for (NodePosition nodePosition : edgePosition.endpoints()) {
      for (EdgePosition externalEdgePosition : nodePosition.protrudes()) {
        if (!externalEdgePosition.equals(edgePosition) && board.getEdgePositions().contains(externalEdgePosition)) {
          adjacentEdges.add(externalEdgePosition);
        }
      }
    }

    return adjacentEdges;
  }
}
