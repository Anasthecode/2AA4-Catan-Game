package com.team22.catan.player;

import java.util.List;
import com.team22.catan.actions.Action;
import com.team22.catan.actions.EndTurn;
import com.team22.catan.actions.GenerateResources;
import com.team22.catan.game.Game;
import com.team22.catan.game.Player;

public class CardLimitSelector extends ActionSelector {

  @Override
  public Action chooseAction(Game game, Player player, List<Action> availableActions) {
    if (player.getResourceCountTotal() > 7) {
      for (Action action : availableActions) {
        if (!(action instanceof EndTurn) && !(action instanceof GenerateResources)) {
          return action;
        }
      }
    }

    if (nextHandler != null) {
      return nextHandler.chooseAction(game, player, availableActions);
    }

    return null;
  }
}