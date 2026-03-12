package com.team22.catan.game;

/**
 * Demonstrator class for Assignment 2.
 * This class serves as the entry point for the simulation. It utilizes the GameFactory
 * to instantiate a game with one Human player and automated Computer players.
 * It demonstrates the integration of the visualizer (via the Decorator pattern),
 * the human-in-the-loop CLI parsing, and the core game state machine.
 */
public class Demonstrator {
  public static void main(String[] args) {
    GameFactory gameFactory = new GameFactory(
        CatanSettings.RNG_SEED, CatanSettings.DEFAULT_PLAYER_COUNT);
    
    Game game = gameFactory.createGame();
    game.play();
  }
}