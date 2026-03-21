package com.team22.catan.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.board.Board;
import com.team22.catan.board.EdgePosition;
import com.team22.catan.game.Game;
import com.team22.catan.game.Player;

public class LongestRoadSelector extends ActionSelector {

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

    List<EdgePosition> longestRoad = board.longestRoad(player);
    for (Player otherPlayer : game.getPlayers()) {
      if (player.equals(otherPlayer)) {
        continue;
      }

      List<EdgePosition> otherLongestRoad = board.longestRoad(otherPlayer);
      if (otherLongestRoad.size() < longestRoad.size() - 1 ||
          otherLongestRoad.size() > longestRoad.size()) {
        continue;
      }

      for (EdgePosition longestRoadEdgePosition : longestRoad) {
        for (EdgePosition otherEdgePosition : board.getEdgePositions()) {
          if (longestRoadEdgePosition.connectsTo(otherEdgePosition) &&
              buildRoadActions.containsKey(otherEdgePosition)) {
            return buildRoadActions.get(otherEdgePosition);
          }
        }
      }
    }

    if (nextHandler != null) {
      return nextHandler.chooseAction(game, player, availableActions);
    }

    return null;
  }
}
