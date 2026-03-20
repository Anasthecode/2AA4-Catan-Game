package com.team22.catan.game;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildCity;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.actions.BuildSettlement;

public class StandardActionEvaluator implements ActionEvaluator {

    @Override
    public double evaluate(Action action, Player player) {
        double highestValue = 0.0;

        // Rule 1: Earning a VP (1.0)
        if (action instanceof BuildSettlement || action instanceof BuildCity) {
            highestValue = Math.max(highestValue, 1.0);
        }
        // Rule 2: Building something without earning a VP (0.8)
        else if (action instanceof BuildRoad) {
            highestValue = Math.max(highestValue, 0.8);
        }

        // Rule 3: Spending cards so that < 5 remain (0.5)
        int currentCards = player.getResourceCountTotal();
        int actionCost = calculateCost(action);

        if (currentCards >= 5 && (currentCards - actionCost) < 5) {
            highestValue = Math.max(highestValue, 0.5);
        }

        return highestValue;
    }

    /**
     * Helper method to determine how many cards an action costs.
     */
    private int calculateCost(Action action) {
        if (action instanceof BuildCity) {
            return 5; // 3 Ore, 2 Wheat
        } else if (action instanceof BuildSettlement) {
            return 4; // 1 Wood, 1 Brick, 1 Wheat, 1 Sheep
        } else if (action instanceof BuildRoad) {
            return 2; // 1 Wood, 1 Brick
        }
        return 0; // EndTurn, GenerateResources, etc. cost 0
    }
}