package com.team22.catan.player;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.actions.BuildSettlement;
import com.team22.catan.actions.EndTurn;
import com.team22.catan.actions.GenerateResources;
import com.team22.catan.game.Game;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;

public class HumanPlayer extends Player {

  private Parser parser;

  public HumanPlayer(String name, PlayerColor color, Parser parser) {
    super(name, color);
    this.parser = parser;
  }

  @Override
  public void onTurn(Game game) {

    System.out.println("It is your turn, " + getName() + ".");

    if (game.getState() == GameState.SETUP) {
      setupTurn(game);
    } else if (getDiceRolled()) {
      System.out.println("Enter a command (List, Build [type] [id], Go):");
      Action action = parser.parseCommand(this, game);

      if (action instanceof GenerateResources) {
        System.out.println("Already rolled dice this turn!");
      } else if (action != null) {
        game.executeAction(action);
      }

    } else {
      System.out.println("Enter a command (Roll, List, Build [type] [id]):");
      Action action = parser.parseCommand(this, game);
      if (action != null) {
        game.executeAction(action);
      }
    }
  }

  private void setupTurn(Game game) {
    while (!getSetupSettlement()) {
      System.out.println("Place your initial settlement (Build settlement [id])");

      Action action = parser.parseCommand(this, game);

      if (action != null && !(action instanceof BuildSettlement)) {
        System.out.println("Cannot do that right now!");
      } else if (action != null) {
        game.executeAction(action);
      }
    }

    while (!getSetupRoad()) {
      System.out.println("Place your initial road connecting to the settlement (Build road [id, id])");

      Action action = parser.parseCommand(this, game);

      if (action != null && !(action instanceof BuildRoad)) {
        System.out.println("Cannot do that right now!");
      } else if (action != null) {
        game.executeAction(action);
      }
    }

    game.executeAction(new EndTurn(this, game));
  }
}