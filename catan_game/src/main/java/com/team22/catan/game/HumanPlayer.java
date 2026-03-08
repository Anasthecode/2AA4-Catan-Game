package com.team22.catan.game;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.EndTurn;

public class HumanPlayer extends Player {

    private Parser parser;

    public HumanPlayer(String name, Parser parser) {
        super(name);
        this.parser = parser;
    }

    @Override
    public void onTurn(Game game) {

        System.out.println("It is your turn, " + getName() + ".");
        boolean turnEnded = false;

        while (!turnEnded && game.getState() != GameState.END) {
            System.out.println("Enter a command (Roll, List, Build [type] [id], End):");
            Action action = parser.parseCommand(this, game);

            if (action != null) {
                action.execute();
                if (action instanceof EndTurn) {
                    turnEnded = true;
                }
            }
        }
    }
}