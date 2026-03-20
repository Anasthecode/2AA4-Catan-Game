package com.team22.catan.player;

import java.util.List;
import com.team22.catan.actions.Action;
import com.team22.catan.game.Game;
import com.team22.catan.game.Player;

/**
 * Chain of Responsibility pattern for AI constraints
 */
public abstract class ActionSelector {
    protected ActionSelector nextHandler;

    public void setNext(ActionSelector nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract Action chooseAction(Game game, Player player, List<Action> availableActions);
}