package com.team22.catan.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import com.team22.catan.game.Game;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.game.Resource;

public class GenerateResources implements Action {
  private Player roller;
  private Game game;
  private Random rng;

  private int roll;
  private Map<Player, Map<Resource, Integer>> initialPlayerInventories;

  public GenerateResources(Player roller, Game game) {
    this.roller = roller;
    this.game = game;
    rng = game.getRng();

    roll = 0;

    initialPlayerInventories = new HashMap<>();
    for (Player player : game.getPlayers()) {
      initialPlayerInventories.put(player, player.getInventory());
    }
  }

  @Override
  public boolean execute() {
    if (game.getState() == GameState.PLAYING) {
      System.out.println("Roll: " + roll);
      if (roll == 0) {
        roll = game.rollDice();
      }

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
  public void undo() {
    System.out.println("Undoing dice roll " + roll);
    // Old and new inventories are guaranteed to have all possible resources, so no need to check
    for (Player player : game.getPlayers()) {
      Map<Resource, Integer> newInventory = player.getInventory();
      Map<Resource, Integer> oldInventory = initialPlayerInventories.get(player);
      
      for (Entry<Resource, Integer> resource : newInventory.entrySet()) {
        int difference = resource.getValue() - oldInventory.get(resource.getKey());
        player.addResource(resource.getKey(), -difference);
      }
    }

    roller.setDiceRolled(false);
  }
}
