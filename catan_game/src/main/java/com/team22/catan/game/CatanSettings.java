package com.team22.catan.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.team22.catan.board.TileType;

public class CatanSettings {
  private static final Properties config = new Properties();

  static {
    try {
      //FileReader reader = new FileReader("catan_game/res/config.properties");

      InputStream input = CatanSettings.class.getClassLoader().getResourceAsStream("config.properties");

      config.load(input);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static final int TURNS = Math.min(Integer.valueOf(config.getProperty("turns")), 8192);
  public static final int WINNING_VP_COUNT = Integer.valueOf(config.getProperty("winning_vp"));
  public static final long RNG_SEED = Long.valueOf(config.getProperty("rng_seed"));

  public static final int DEFAULT_PLAYER_COUNT = Integer.valueOf(config.getProperty("default_player_count"));

  public static final int NUMBER_OF_DICE = 2;

  public static final int[] TOKEN_LAYOUT = {3, 4, 3, 4, 6, 11,
      5, 6, 11, 5, 8, 10, 9, 2, 10, 12, 9, 8};
  public static final TileType[] STANDARD_BOARD_LAYOUT = {
    TileType.DESERT,
    TileType.FIELD,
    TileType.MOUNTAIN,
    TileType.FOREST,
    TileType.HILLS,
    TileType.PASTURE,
    TileType.FOREST,
    TileType.PASTURE,
    TileType.FIELD,
    TileType.HILLS,
    TileType.FOREST,
    TileType.FIELD,
    TileType.FIELD,
    TileType.MOUNTAIN,
    TileType.PASTURE,
    TileType.FOREST,
    TileType.HILLS,
    TileType.MOUNTAIN,
    TileType.PASTURE
  };
}