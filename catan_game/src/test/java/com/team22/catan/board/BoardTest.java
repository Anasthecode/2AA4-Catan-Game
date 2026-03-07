package com.team22.catan.board;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.team22.catan.game.CatanSettings;

public class BoardTest {
  @Test
  public void boardTilePositionTest() {
    TileType[] layout = CatanSettings.STANDARD_BOARD_LAYOUT;
    int[] tokens = CatanSettings.TOKEN_LAYOUT;
    Board board = new Board(layout, tokens);

    List<Tile> orderedBoardTiles = board.getOrderedTiles();
    List<AxialPosition> expectedPositions = Arrays.asList(
      new AxialPosition(0, 0),
      new AxialPosition(-1, 1),
      new AxialPosition(0, 1),
      new AxialPosition(1, 0),
      new AxialPosition(1, -1),
      new AxialPosition(0, -1),
      new AxialPosition(-1, 0),
      new AxialPosition(-2, 2),
      new AxialPosition(-1, 2),
      new AxialPosition(0, 2),
      new AxialPosition(1, 1),
      new AxialPosition(2, 0),
      new AxialPosition(2, -1),
      new AxialPosition(2, -2),
      new AxialPosition(1, -2),
      new AxialPosition(0, -2),
      new AxialPosition(-1, -1),
      new AxialPosition(-2, 0),
      new AxialPosition(-2, 1)
    );

    assertEquals(expectedPositions.size(), orderedBoardTiles.size());
    for (int i = 0; i < expectedPositions.size(); i++) {
      assertEquals(expectedPositions.get(i), orderedBoardTiles.get(i).getPosition());
    }
  }

  @Test
  public void boardTileTypeTest() {
    TileType[] layout = CatanSettings.STANDARD_BOARD_LAYOUT;
    int[] tokens = CatanSettings.TOKEN_LAYOUT;

    Board board = new Board(layout, tokens);
    List<Tile> orderedBoardTiles = board.getOrderedTiles();
    
    assertEquals(layout.length, orderedBoardTiles.size());
    for (int i = 0; i < layout.length; i++) {
      assertEquals(layout[i], orderedBoardTiles.get(i).getTileType());
    }
  }

  @Test
  public void boardTileTokenTest() {
    TileType[] layout = CatanSettings.STANDARD_BOARD_LAYOUT;
    int[] tokens = CatanSettings.TOKEN_LAYOUT;

    Board board = new Board(layout, tokens);
    List<Tile> orderedBoardTiles = board.getOrderedTiles();

    assertEquals(tokens.length, orderedBoardTiles.size() -
        Collections.frequency(Arrays.asList(layout), TileType.DESERT));
    
    int tokenIndex = 0;
    for (Tile tile : orderedBoardTiles) {
      if (tile.getTileType() == TileType.DESERT) {
        assertEquals(0, tile.getToken());
      } else {
        assertEquals(tokens[tokenIndex], tile.getToken());
        tokenIndex++;
      }
    }
  }
}
