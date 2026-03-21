package com.team22.catan.player;

import java.util.List;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.board.Board;
import com.team22.catan.game.Game;
import com.team22.catan.game.Player;

public class LongestRoadSelector extends ActionSelector {

  @Override
  public Action chooseAction(Game game, Player player, List<Action> availableActions) {
    Board board = game.getBoard();
    int longestRoad = board.longestRoad(player);
    for (Player otherPlayer : game.getPlayers()) {
      if (player.equals(otherPlayer)) {
        continue;
      }

      int otherLength = board.longestRoad(otherPlayer);
      if (otherLength >= longestRoad - 1 && otherLength <= longestRoad) {
        for (Action action : availableActions) {
          if (action instanceof BuildRoad) {
            return action;
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
