package com.team22.catan.actions;

import com.team22.catan.game.Player;

public class DisplayInventory implements Action {
  private Player player;

  public DisplayInventory(Player player) {
    this.player = player;
  }

  @Override
  public boolean execute() {
    System.out.println("Inventory: \n" + player.getInventory().toString());
    return true;
  }

  @Override
  public void undo() {
    // Since this doesn't change the state, no need to worry about it
    System.out.println("Inventory: \n" + player.getInventory().toString());
  }
}
