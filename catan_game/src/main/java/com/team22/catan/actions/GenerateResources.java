package com.team22.catan.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.team22.catan.game.Game;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.game.Resource;

public class GenerateResources implements Action {
  private Player roller;
  private Game game;

  private Random rng;

  public GenerateResources(Player roller, Game game) {
    this.roller = roller;
    this.game = game;
    rng = game.getRng();
  }

  @Override
  public boolean execute() {
    if (game.getState() == GameState.PLAYING) {
      int roll = game.rollDice();
      System.out.println(roller.getName() + " rolled a " + roll);

      if (roll == 7) {
        System.out.println("A 7 was rolled! The Robber is striking.");
        Set<Player> stealablePlayers = game.getBoard().moveRobberToRandomTile(rng);
        robPlayers(stealablePlayers);
      } else {
        game.getBoard().notifyTilesOfRoll(roll);
      }

      roller.setDiceRolled(true);
      return true;
    } else {
      System.out.println("Cannot roll the dice right now");
      return false;
    }
  }

  private void robPlayers(Set<Player> stealablePlayers) {
    for (Player player : game.getPlayers()) {
      if (player.getResourceCountTotal() > 7) {
        System.out.println(player.getName() + " has too many cards and loses half!");
        player.dropHalfResources(rng);
      }
    }

    Set<Player> temp = new HashSet<>();
    for (Player player : stealablePlayers) {
      if (player != roller && player.getResourceCountTotal() > 0) {
        temp.add(player);
      }
    }

    List<Player> stealablePlayersList = new ArrayList<>(temp);

    if (!stealablePlayersList.isEmpty()) {
      Player target = stealablePlayersList.get(rng.nextInt(stealablePlayersList.size()));
      Resource stolenResource = target.stealRandomResource(rng);

      if (stolenResource != null) {
        roller.addResource(stolenResource, 1);
        System.out.println(roller.getName() + " stole 1 " + stolenResource + " from " + target.getName());
      }

    } else {
      System.out.println("No one to steal from!");
    }
  }

  @Override
  public void unExecute() {

  }

}
