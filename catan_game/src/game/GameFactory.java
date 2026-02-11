package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import board.Board;

public class GameFactory {
  private Random rng;
  private int numberOfPlayers;

  public GameFactory(int seed, int numberOfPlayers) {
    rng = new Random(seed);
    this.numberOfPlayers = numberOfPlayers;
  }

  public Game createGame() {
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(new ComputerPlayer("Player " + (i + 1), rng));
    }

    Dice dice = new Dice(rng);

    return new Game(CatanSettings.TURNS, new Board(), players, dice);
  }
}
