package com.team22.catan.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.team22.catan.actions.Action;

public class ValueEvaluationHandler extends ConstraintHandler {
    private ActionEvaluator evaluator;
    private Random rng;

    public ValueEvaluationHandler(ActionEvaluator evaluator, Random rng) {
        this.evaluator = evaluator;
        this.rng = rng;
    }

    @Override
    public Action handleRequest(Game game, Player player, List<Action> availableActions) {
        double highestScore = -1.0;
        List<Action> tiedBestActions = new ArrayList<>();

        for (Action action : availableActions) {
            double score = evaluator.evaluate(action, player);

            if (score > highestScore) {
                highestScore = score;
                tiedBestActions.clear();
                tiedBestActions.add(action);
            } else if (score == highestScore) {
                tiedBestActions.add(action);
            }
        }

        if (tiedBestActions.isEmpty()) {
            return null;
        }
        return tiedBestActions.get(rng.nextInt(tiedBestActions.size()));
    }
}