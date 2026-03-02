package com.team22.catan.game;

public class Demonstrator {
  public static void main(String[] args) {
    GameFactory gameFactory = new GameFactory(
        CatanSettings.RNG_SEED, CatanSettings.DEFAULT_PLAYER_COUNT);
    
    Game game = gameFactory.createGame();
    game.play();
  }
}
