package com.team22.catan.actions;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.team22.catan.board.Board;
import com.team22.catan.board.Node;
import com.team22.catan.board.NodePosition;
import com.team22.catan.game.Player;
import com.team22.catan.structures.City;

public class BuildCityTest {
    // Mock all relevant classes other than BuildCity, make sure they aren't null later on in setup
    @Mock
    private Player player;

    @Mock
    private Board board;

    @Mock
    private Node node;

    @Mock
    private NodePosition nodePosition;

    @Mock
    private Map<NodePosition, Node> nodes;

    private BuildCity buildCity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks so that they aren't null

        when(board.getNodes()).thenReturn(nodes); // Get the nodes map
        when(nodes.get(nodePosition)).thenReturn(node); // Get a specific node out of the map

        buildCity = new BuildCity(player, board, nodePosition);
    }


    @Test
    public void executePlayerCannotAffordCity() {
        /* This one tests the fail case of the player not being able to afford the city, nothing to do with position*/
        when(player.canAfford(any())).thenReturn(false);
        when(player.getName()).thenReturn("CannotAfford");

        buildCity.execute();

        verify(player).canAfford(any());
        verify(player, never()).build(any(City.class));
        verify(node, never()).placeCity(any());
    }

    @Test
    public void executePlayerCanAffordButInvalid() {
        /* Tests if player can afford the city but cannot place it*/
        when(player.canAfford((any()))).thenReturn(true);
        when(player.getName()).thenReturn("InvalidPlacement");
        when(node.canPlaceCity(player)).thenReturn(false);

        buildCity.execute();

        verify(player).canAfford(any());
        verify(player, never()).build(any(City.class));
        verify(node, never()).placeCity(any());
    }

    @Test
    public void executePlayerCanAffordAndValid() {
        /* Tests if it is a successful build*/
        when(player.canAfford(any())).thenReturn(true);
        when(player.getName()).thenReturn("CanAffordAndPlace");
        when(node.canPlaceCity(player)).thenReturn(true);

        buildCity.execute();

        verify(player).build(any(City.class));
        verify(player).addVictoryPoints(1);
        verify(node).placeCity(any(City.class));
    }
}