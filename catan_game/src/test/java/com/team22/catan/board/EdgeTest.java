package com.team22.catan.board;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * These tests follow the relations defined in https://www.redblobgames.com/grids/parts/
  */
public class EdgeTest {
  @Test
  public void endpointsNorthEastTest() {
    Edge edge = new Edge(new EdgePosition(0, 0, RelativeEdgeLocation.NORTHEAST));
    List<NodePosition> endpoints = edge.endpoints();
    assertEquals(new NodePosition(1, -1, RelativeNodeLocation.SOUTH), endpoints.get(0));
    assertEquals(new NodePosition(0, 0,  RelativeNodeLocation.NORTH), endpoints.get(1));
  }

  @Test
  public void endpointsNorthWestTest() {
    Edge edge = new Edge(new EdgePosition(0, 0, RelativeEdgeLocation.NORTHEAST));
    List<NodePosition> endpoints = edge.endpoints();
    assertEquals(new NodePosition(0, 0,  RelativeNodeLocation.NORTH), endpoints.get(0));
    assertEquals(new NodePosition(0, -1, RelativeNodeLocation.SOUTH), endpoints.get(1));
  }

  @Test
  public void endpointsWestTest() {
    Edge edge = new Edge(new EdgePosition(0, 0, RelativeEdgeLocation.NORTHEAST));
    List<NodePosition> endpoints = edge.endpoints();
    assertEquals(new NodePosition(0, -1,  RelativeNodeLocation.SOUTH), endpoints.get(0));
    assertEquals(new NodePosition(-1, 1,  RelativeNodeLocation.NORTH), endpoints.get(1));
  }
}
