package com.team22.catan.actions;

import static org.mockito.Mockito.*;
import java.util.EnumMap;
import com.team22.catan.game.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.team22.catan.board.Board;
import com.team22.catan.board.NodePosition;
import com.team22.catan.game.Player;
import com.team22.catan.structures.City;

public class BuildCityTest {
    // Mock all relevant classes other than BuildCity, make sure they aren't null later on in setup
    @Mock
    private Player player;

    @Mock
    private Game game;

    @Mock
    private Board board;

    @Mock
    private NodePosition nodePosition;

    private BuildCity buildCity;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks so that they aren't null
        when(game.getBoard()).thenReturn(board); // Set it so board is not null
        buildCity = new BuildCity(player, game, nodePosition);
    }


    @Test
    public void executePlayerCannotAffordCity() {
        /* This one tests the fail case of the player not being able to afford the city, nothing to do with position*/
        when(player.canAfford(any(EnumMap.class))).thenReturn(false);
        when(player.getName()).thenReturn("CannotAfford");

        buildCity.execute();

        verify(player).canAfford(any());
        verify(player, never()).build(any(City.class));
    }

    @Test
    public void executePlayerCanAffordButInvalid() {
        /* Tests if player can afford the city but cannot place it*/
        when(player.canAfford(any(EnumMap.class))).thenReturn(true);
        when(player.getName()).thenReturn("InvalidPlacement");
        when(board.canPlaceCityAt(nodePosition, player, game.getState())).thenReturn(false);

        buildCity.execute();

        verify(player).canAfford(any());
        verify(player, never()).build(any(City.class));
    }

    @Test
    public void executePlayerCanAffordAndValid() {
        /* Tests if it is a successful build */
        when(player.canAfford(any(EnumMap.class))).thenReturn(true);  // Match any EnumMap
        when(player.getName()).thenReturn("CanAffordAndPlace");
        when(board.canPlaceCityAt(nodePosition, player, game.getState())).thenReturn(true);

        buildCity.execute();

        verify(player, times(1)).build(any(City.class));
        verify(player, times(1)).addVictoryPoints(1);
    }
}