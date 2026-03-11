package com.team22.catan.actions;

import com.team22.catan.board.*;
import com.team22.catan.game.Game;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.structures.Road;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BuildRoadTest {
    @Mock
    private Player player;

    @Mock
    private Game game;

    @Mock
    private Board board;

    @Mock
    private EdgePosition edgePosition;

    private BuildRoad buildRoad;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks so that they aren't null

        when(game.getBoard()).thenReturn(board); // Get the board running
        buildRoad = new BuildRoad(player, game, edgePosition);
    }
    @Test
    public void executeSetupStateWithNoRoadSetup() {
        /* Game is at setup but the player cannot place*/
        when(game.getState()).thenReturn(GameState.SETUP);
        when(player.getName()).thenReturn("CannotPlaceSetupRoad");

        buildRoad.execute();

        verify(board, never()).placeRoadAt(any(EdgePosition.class), any(Road.class));
    }

    @Test
    public void executeSetupStateWithRoad() {
        /* Game is still at setup but the player can place their setup*/
        when(game.getState()).thenReturn(GameState.SETUP);
        when(player.getName()).thenReturn("PlacedSetupRoad");
        when(board.canPlaceRoadAt(any(EdgePosition.class), any(Player.class))).thenReturn(true);

        buildRoad.execute();

        verify(board).placeRoadAt(any(EdgePosition.class), any(Road.class));
    }

    @Test
    public void executeCannotAfford() {
        /* Game moved on but the player doesn't have the resources */
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(false);
        when(player.getName()).thenReturn("CannotAfford");

        buildRoad.execute();

        verify(player).canAfford(any());
        verify(player, never()).build(any(Road.class));
        verify(board, never()).placeRoadAt(any(EdgePosition.class), any(Road.class));
    }

    @Test
    public void executeCanAffordButInvalid() {
        /* Game is in the right state but the player is not a valid edge to place a road*/
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(true);
        when(player.getName()).thenReturn("CannotPlace");

        buildRoad.execute();

        verify(player).canAfford(any());
        verify(player, never()).build(any(Road.class));
        verify(board, never()).placeRoadAt(any(EdgePosition.class), any(Road.class));
    }

    @Test
    public void executeCanAffordAndValid() {
        /* Player has everything they need to place a road */
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(true);
        when(player.getName()).thenReturn("CanPlace");
        when(board.canPlaceRoadAt(edgePosition, player)).thenReturn(true);

        buildRoad.execute();

        verify(player).canAfford(any());
        verify(board).placeRoadAt(any(EdgePosition.class), any(Road.class));
    }
}