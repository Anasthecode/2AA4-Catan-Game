package com.team22.catan.board;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TileTest {
  @Test
  public void cornersTest() {
    Tile tile = new Tile(new AxialPosition(0, 0), 0, null);
    List<NodePosition> corners = tile.corners();
    assertEquals(new NodePosition(1, -1, RelativeNodeLocation.SOUTH), corners.get(0));
    assertEquals(new NodePosition(0, 0,  RelativeNodeLocation.NORTH), corners.get(1));
    assertEquals(new NodePosition(0, -1, RelativeNodeLocation.SOUTH), corners.get(2));
    assertEquals(new NodePosition(-1, 1, RelativeNodeLocation.NORTH), corners.get(3));
    assertEquals(new NodePosition(0, 0,  RelativeNodeLocation.SOUTH), corners.get(4));
    assertEquals(new NodePosition(0, 1,  RelativeNodeLocation.NORTH), corners.get(5));
  }

  @Test
  public void bordersTest() {
    Tile tile = new Tile(new AxialPosition(0, 0), 0, null);
    List<EdgePosition> borders = tile.borders();
    assertEquals(new EdgePosition(1, 0,  RelativeEdgeLocation.WEST),      borders.get(0));
    assertEquals(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHEAST), borders.get(1));
    assertEquals(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHWEST), borders.get(2));
    assertEquals(new EdgePosition(0, 0,  RelativeEdgeLocation.WEST),      borders.get(3));
    assertEquals(new EdgePosition(-1, 1, RelativeEdgeLocation.NORTHEAST), borders.get(4));
    assertEquals(new EdgePosition(0, 1,  RelativeEdgeLocation.NORTHWEST), borders.get(5));
  }
}
