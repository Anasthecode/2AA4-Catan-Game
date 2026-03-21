package com.team22.catan.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.team22.catan.actions.Action;
import com.team22.catan.actions.BuildCity;
import com.team22.catan.actions.BuildRoad;
import com.team22.catan.actions.BuildSettlement;
import com.team22.catan.actions.EndTurn;
import com.team22.catan.actions.GenerateResources;
import com.team22.catan.board.EdgePosition;
import com.team22.catan.board.NodePosition;
import com.team22.catan.game.Game;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.structures.City;
import com.team22.catan.structures.Road;
import com.team22.catan.structures.Settlement;

public class ComputerPlayer extends Player {

  private Random rng;
  private Parser parser;

  private ActionSelector aiBrain;

  public ComputerPlayer(String name, PlayerColor color, Random rng, Parser parser) {
    super(name, color);
    this.rng = rng;
    this.parser = parser;

    ActionEvaluator standardEvaluator = new StandardActionEvaluator();

    ActionSelector adjacentRoadsNode = new RoadConnectionSelector();
    ActionSelector cardLimitNode = new CardLimitSelector();
    ActionSelector valueNode = new ActionScoreSelector(standardEvaluator, rng);

    adjacentRoadsNode.setNext(cardLimitNode);
    cardLimitNode.setNext(valueNode);

    this.aiBrain = adjacentRoadsNode;
  }

  @Override
  public void onTurn(Game game) {

    parser.waitForGoCommand(game);

    List<Action> availableActions = getAvailableActions(game);

    if (game.getState() == GameState.SETUP) {
      setupTurn(game);
    } else {
      if (availableActions.isEmpty()) {
        game.executeAction(new EndTurn(this, game));
        return;
      }

      Action chosenAction = aiBrain.chooseAction(game, this, availableActions);

      if (chosenAction != null) {
        game.executeAction(chosenAction);
      } else {
        game.executeAction(new EndTurn(this, game));
      }
    }
  }

  private void setupTurn(Game game) {
    List<Action> possibleSettlementPlacements = new ArrayList<>();
    List<Action> possibleRoadPlacements = new ArrayList<>();

    if (!getSetupSettlement()) {
      for (NodePosition nodePosition : game.getBoard().getNodePositions()) {
        if (game.getBoard().canPlaceSettlementAt(nodePosition, this, game.getState())) {
          possibleSettlementPlacements.add(new BuildSettlement(this, game, nodePosition));
        }
      }

      game.executeAction(possibleSettlementPlacements.get(rng.nextInt(possibleSettlementPlacements.size())));
    }

    if (!getSetupRoad()) {
      for (EdgePosition edgePosition : game.getBoard().getEdgePositions()) {
        if (game.getBoard().canPlaceRoadAt(edgePosition, this, game.getState())) {
          possibleRoadPlacements.add(new BuildRoad(this, game, edgePosition));
        }
      }

      game.executeAction(possibleRoadPlacements.get(rng.nextInt(possibleRoadPlacements.size())));
    }

    game.executeAction(new EndTurn(this, game));
  }

  private List<Action> getAvailableActions(Game game) {
    List<Action> actionsToReturn = new ArrayList<>();

    if (!getDiceRolled()) {
      actionsToReturn.add(new GenerateResources(this, game));
    }

    for (NodePosition nodePosition : game.getBoard().getNodePositions()) {
      if (game.getBoard().canPlaceSettlementAt(nodePosition, this, game.getState()) &&
          canAfford(new Settlement(this).getCost())) {

        actionsToReturn.add(new BuildSettlement(this, game, nodePosition));
      }

      if (game.getBoard().canPlaceCityAt(nodePosition, this, game.getState()) &&
          canAfford(new City(this).getCost())) {

        actionsToReturn.add(new BuildCity(this, game, nodePosition));
      }
    }

    for (EdgePosition edgePosition : game.getBoard().getEdgePositions()) {
      if (game.getBoard().canPlaceRoadAt(edgePosition, this, game.getState()) &&
          canAfford(new Road(this).getCost())) {

        actionsToReturn.add(new BuildRoad(this, game, edgePosition));
      }
    }

    if (getDiceRolled()) {
      actionsToReturn.add(new EndTurn(this, game));
    }

    return actionsToReturn;
  }
}