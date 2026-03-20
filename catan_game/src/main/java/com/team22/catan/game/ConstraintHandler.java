package com.team22.catan.game;

import java.util.List;
import com.team22.catan.actions.Action;

/**
 * Chain of Responsibility pattern for AI constraints
 */
public abstract class ConstraintHandler {
    protected ConstraintHandler nextHandler;

    public void setNext(ConstraintHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract Action handleRequest(Game game, Player player, List<Action> availableActions);
}