package com.team22.catan.game;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildCity;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.actions.BuildSettlement;
import com.team22.catan.actions.DisplayInventory;
import com.team22.catan.actions.GenerateResources;
import com.team22.catan.board.NodePosition;
import com.team22.catan.board.RelativeNodeLocation;
import com.team22.catan.structures.Settlement;

public class ParserTest {
  @Test
  public void parseRollAction() {
    Parser parser = new Parser();
    Game game = new GameFactory(0, 4).createGame();
    Action action = parser.parseCommandFromString(game.getPlayers().get(0), game, "Roll");
    assertTrue(action instanceof GenerateResources);
  }
  
  @Test
  public void parseListAction() {
    Parser parser = new Parser();
    Game game = new GameFactory(0, 4).createGame();
    Action action = parser.parseCommandFromString(game.getPlayers().get(0), game, "list");
    assertTrue(action instanceof DisplayInventory);
  }

  @Test
  public void parseBuildSettlementAction() {
    Parser parser = new Parser();
    Game game = new GameFactory(0, 4).createGame();
    Action action = parser.parseCommandFromString(game.getPlayers().get(0), game, "Build settlement 0");
    assertTrue(action instanceof BuildSettlement);
  }

  @Test
  public void parseBuildRoadAction() {
    Parser parser = new Parser();
    Game game = new GameFactory(0, 4).createGame();
    Action action = parser.parseCommandFromString(game.getPlayers().get(0), game, "Build road 0,1");
    assertTrue(action instanceof BuildRoad);
  }

  @Test
  public void parseBuildCityAction() {
    Parser parser = new Parser();
    Game game = new GameFactory(0, 4).createGame();
    game.getBoard().placeSettlementAt(new NodePosition(1, -1, RelativeNodeLocation.SOUTH),
        new Settlement(game.getPlayers().get(0)), game.getState());
    Action action = parser.parseCommandFromString(game.getPlayers().get(0), game, "Build city 0");
    assertTrue(action instanceof BuildCity);
  }
}
