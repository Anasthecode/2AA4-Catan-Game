package com.team22.catan.game;

/**
 * Demonstrator for Assignment 2.
 * This class initializes a Catan game with one Human player and
 * computer-controlled opponents using a GameFactory. It demonstrates
 * the state machine (SETUP, PLAYING, END), the Decorator pattern for
 * JSON visualization, and human-in-the-loop command parsing.
 */
public class Demonstrator {
  public static void main(String[] args) {
    GameFactory gameFactory = new GameFactory(
        CatanSettings.RNG_SEED, CatanSettings.DEFAULT_PLAYER_COUNT);
    
    Game game = gameFactory.createGame();
    game.play();
  }
}
