package com.team22.catan.game;

import java.util.List;
import com.team22.catan.actions.Action;
import com.team22.catan.actions.EndTurn;
import com.team22.catan.actions.GenerateResources;

public class CardLimitHandler extends ConstraintHandler {

    @Override
    public Action handleRequest(Game game, Player player, List<Action> availableActions) {
        if (player.getResourceCountTotal() > 7) {
            for (Action action : availableActions) {
                if (!(action instanceof EndTurn) && !(action instanceof GenerateResources)) {
                    System.out.println(player.getName() + " triggered Constraint: Must spend cards!");
                    return action;
                }
            }
        }

        if (nextHandler != null) {
            return nextHandler.handleRequest(game, player, availableActions);
        }
        return null;
    }
}