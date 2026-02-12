package game;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import board.TileType;

public class CatanSettings {
  private static final Properties config = new Properties();

  static {
    try {
      FileReader reader = new FileReader("catan_game/res/config.properties");
      config.load(reader); 
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static final int TURNS = Math.min(Integer.valueOf(config.getProperty("turns")), 8192);
  public static final int WINNING_VP_COUNT = Integer.valueOf(config.getProperty("winning_vp"));
  public static final long RNG_SEED = Long.valueOf(config.getProperty("rng_seed"));

  public static final int DEFAULT_PLAYER_COUNT = Integer.valueOf(config.getProperty("default_player_count"));

  public static final int BOARD_RADIUS = 2;
  public static final int[] TOKEN_LAYOUT = {3, 4, 3, 4, 6, 11,
      5, 6, 11, 5, 8, 10, 9, 2, 10, 12, 9, 8};
  public static final TileType[] STANDARD_BOARD_LAYOUT = {
    TileType.DESERT,
    TileType.MOUNTAIN,
    TileType.FIELD,
    TileType.FOREST,
    TileType.PASTURE,
    TileType.HILLS,
    TileType.FOREST,
    TileType.HILLS,
    TileType.FIELD,
    TileType.PASTURE,
    TileType.PASTURE,
    TileType.MOUNTAIN,
    TileType.HILLS,
    TileType.FOREST,
    TileType.PASTURE,
    TileType.MOUNTAIN,
    TileType.FIELD,
    TileType.FIELD,
    TileType.FOREST
  };
}