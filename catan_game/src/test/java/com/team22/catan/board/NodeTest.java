package com.team22.catan.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.team22.catan.game.ComputerPlayer;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Parser;
import com.team22.catan.game.Player;
import com.team22.catan.game.Player.PlayerColor;
import com.team22.catan.structures.Road;
import com.team22.catan.structures.Settlement;

public class NodeTest {
  @Test
  public void distanceRuleTestSuccess() {
    List<Edge> nodeEdges = Arrays.asList(
      new Edge(new EdgePosition(1, -1, RelativeEdgeLocation.WEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHWEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHEAST))
    );
    
    Node node = new Node(0, 0, RelativeNodeLocation.NORTH, nodeEdges);
    for (Edge edge : nodeEdges) {
      edge.setNodes(node, new Node(new NodePosition(1, 1, RelativeNodeLocation.SOUTH)));
    }

    assertTrue(node.distanceRule());
  }

  @Test
  public void distanceRuleTestFailure() {
    List<Edge> nodeEdges = Arrays.asList(
      new Edge(new EdgePosition(1, -1, RelativeEdgeLocation.WEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHWEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHEAST))
    );
    
    Node node = new Node(0, 0, RelativeNodeLocation.NORTH, nodeEdges);
    for (Edge edge : nodeEdges) {
      Node endNode = new Node(new NodePosition(1, 1, RelativeNodeLocation.SOUTH));
      endNode.placeSettlement(new Settlement(
          new ComputerPlayer("p", PlayerColor.BLUE, new Random(), new Parser())), GameState.SETUP);
      edge.setNodes(node, endNode);
    }

    assertFalse(node.distanceRule());
  }

  @Test
  public void placeSettlementTestSuccessSetup() {
    List<Edge> nodeEdges = Arrays.asList(
      new Edge(new EdgePosition(1, -1, RelativeEdgeLocation.WEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHWEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHEAST))
    );
    
    Node node = new Node(0, 0, RelativeNodeLocation.NORTH, nodeEdges);
    Player player = new ComputerPlayer("p", PlayerColor.BLUE, new Random(), new Parser());
    for (Edge edge : nodeEdges) {
      edge.setNodes(node, new Node(new NodePosition(1, 1, RelativeNodeLocation.SOUTH)));
    }

    node.placeSettlement(new Settlement(player), GameState.SETUP);
    assertTrue(node.hasStructure());
  }

  @Test
  public void placeSettlementTestSuccessPlaying() {
    List<Edge> nodeEdges = Arrays.asList(
      new Edge(new EdgePosition(1, -1, RelativeEdgeLocation.WEST)),
      new Edge(new EdgePosition(0, 0, RelativeEdgeLocation.NORTHWEST))
    );

    Player player = new ComputerPlayer("p", PlayerColor.BLUE, new Random(), new Parser());

    Node startingNode = new Node(1, -2, RelativeNodeLocation.SOUTH,
        Arrays.asList(nodeEdges.get(0)));
    Node endingNode = new Node(0, -1, RelativeNodeLocation.SOUTH,
        Arrays.asList(nodeEdges.get(1)));
    Node middleNode = new Node(0, 0, RelativeNodeLocation.NORTH, nodeEdges);
    
    nodeEdges.get(0).setNodes(startingNode, middleNode);
    nodeEdges.get(1).setNodes(middleNode, endingNode);
        
    startingNode.placeSettlement(new Settlement(player), GameState.SETUP);
    nodeEdges.get(0).placeRoad(new Road(player), GameState.PLAYING);
    nodeEdges.get(1).placeRoad(new Road(player), GameState.PLAYING);

    endingNode.placeSettlement(new Settlement(player), GameState.PLAYING);
    assertTrue(endingNode.hasStructure());
  }

  @Test(expected = IllegalStateException.class)
  public void placeSettlementTestFailureNoConnectingRoads() {
    List<Edge> nodeEdges = Arrays.asList(
      new Edge(new EdgePosition(1, -1, RelativeEdgeLocation.WEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHWEST)),
      new Edge(new EdgePosition(0, 0,  RelativeEdgeLocation.NORTHEAST))
    );
    
    Node node = new Node(0, 0, RelativeNodeLocation.NORTH, nodeEdges);
    Player player = new ComputerPlayer("p", PlayerColor.BLUE, new Random(), new Parser());
    
    node.placeSettlement(new Settlement(player), GameState.PLAYING);
  }
}
