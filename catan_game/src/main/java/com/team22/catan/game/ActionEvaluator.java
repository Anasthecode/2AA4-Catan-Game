package com.team22.catan.game;

import com.team22.catan.actions.Action;

/**
 * Strategy Pattern Interface for evaluating the value of an action.
 */
public interface ActionEvaluator {
    /**
     * Evaluates an action and returns a score representing its value.
     */
    double evaluate(Action action, Player player);
}