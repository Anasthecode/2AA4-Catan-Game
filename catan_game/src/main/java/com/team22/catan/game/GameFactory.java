package com.team22.catan.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.team22.catan.board.Board;
import com.team22.catan.board.CatanBoard;
import com.team22.catan.board.VisualizerDecorator;
import com.team22.catan.game.Player.PlayerColor;

public class GameFactory {
  private Random rng;
  private int numberOfPlayers;

  public GameFactory(long seed, int numberOfPlayers) {
    rng = new Random(seed);
    this.numberOfPlayers = numberOfPlayers;
  }

  public Game createGame() {
      List<Player> players = new ArrayList<>();
      Parser parser = new Parser();

      players.add(new HumanPlayer("Human", PlayerColor.values()[0], parser));

      for (int i = 1; i < numberOfPlayers; i++) {
          players.add(new ComputerPlayer("Computer " + i, PlayerColor.values()[i], rng, parser));
      }

    Dice dice = new Dice(rng);
    Board board = new VisualizerDecorator(new CatanBoard(
        CatanSettings.STANDARD_BOARD_LAYOUT, CatanSettings.TOKEN_LAYOUT));

    return new Game(CatanSettings.TURNS, board, players, dice, rng);
  }
}
