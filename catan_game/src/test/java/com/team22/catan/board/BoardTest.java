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
    Board board = new CatanBoard(layout, tokens);

    List<AxialPosition> orderedBoardTiles = board.getTilePositions();
    List<AxialPosition> expectedPositions = Arrays.asList(
      new AxialPosition(0, 0),
      new AxialPosition(0, 1),
      new AxialPosition(-1, 1),
      new AxialPosition(-1, 0),
      new AxialPosition(0, -1),
      new AxialPosition(1, -1),
      new AxialPosition(1, 0),
      new AxialPosition(0, 2),
      new AxialPosition(-1, 2),
      new AxialPosition(-2, 2),
      new AxialPosition(-2, 1),
      new AxialPosition(-2, 0),
      new AxialPosition(-1, -1),
      new AxialPosition(0, -2),
      new AxialPosition(1, -2),
      new AxialPosition(2, -2),
      new AxialPosition(2, -1),
      new AxialPosition(2, 0),
      new AxialPosition(1, 1)
    );

    assertEquals(expectedPositions.size(), orderedBoardTiles.size());
    for (int i = 0; i < expectedPositions.size(); i++) {
      assertEquals(expectedPositions.get(i), orderedBoardTiles.get(i));
    }
  }

  @Test
  public void catanBoardTileTypeTest() {
    TileType[] layout = CatanSettings.STANDARD_BOARD_LAYOUT;
    int[] tokens = CatanSettings.TOKEN_LAYOUT;

    CatanBoard board = new CatanBoard(layout, tokens);
    TileType[] boardTileTypes = board.getTileTypes();

    for (int i = 0; i < layout.length; i++) {
      assertEquals(layout[i], boardTileTypes[i]);
    }
  }

  @Test
  public void catanBoardTileTokenTest() {
    TileType[] layout = CatanSettings.STANDARD_BOARD_LAYOUT;
    int[] tokens = CatanSettings.TOKEN_LAYOUT;

    CatanBoard board = new CatanBoard(layout, tokens);
    int[] boardTokens = board.getTokens();

    assertEquals(tokens.length, boardTokens.length -
        Collections.frequency(Arrays.asList(layout), TileType.DESERT));
    
    int tokenIndex = 0;
    for (int i = 0; i < boardTokens.length; i++) {
      if (board.getTileTypes()[i] == TileType.DESERT) {
        assertEquals(0, boardTokens[i]);
      } else {
        assertEquals(tokens[tokenIndex], boardTokens[i]);
        tokenIndex++;
      }
    }
  }
}
