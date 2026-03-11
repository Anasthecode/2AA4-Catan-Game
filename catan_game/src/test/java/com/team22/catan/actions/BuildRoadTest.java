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
import java.util.Map;
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
    private Map<EdgePosition, Edge> edges;

    @Mock
    private Edge edge;

    @Mock
    private EdgePosition edgePosition;

    private BuildRoad buildRoad;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks so that they aren't null

        when(game.getBoard()).thenReturn(board); // Go from board to edges to edge

        buildRoad = new BuildRoad(player, game, edgePosition);
    }
    @Test
    public void executeSetupStateWithNoRoadSetup() {
        /* Game is at setup but the player cannot place*/
        Road mockRoad = mock(Road.class);
        when(game.getState()).thenReturn(GameState.SETUP);
        when(edge.getRoad()).thenReturn(mockRoad);
        when(player.getName()).thenReturn("CannotPlaceSetupRoad");

        buildRoad.execute();

        verify(edge, never()).placeRoad(any(Road.class));
    }

    @Test
    public void executeSetupStateWithRoad() {
        /* Game is still at setup but the player can place their setup*/
        when(game.getState()).thenReturn(GameState.SETUP);
        when(edge.getRoad()).thenReturn(null);
        when(player.getName()).thenReturn("PlacedSetupRoad");

        buildRoad.execute();

        verify(edge, times(1)).placeRoad(any(Road.class));
    }

    @Test
    public void executeCannotAfford() {
        /* Game moved on but the player doesn't have the resources */
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(false);
        when(player.getName()).thenReturn("CannotAfford");

        buildRoad.execute();

        verify(player).canAfford(any());
        verify(edge, never()).placeRoad(any(Road.class));
        verify(player, never()).build(any(Road.class));
    }

    @Test
    public void executeCanAffordButInvalid() {
        /* Game is in the right state but the player is not a valid edge to place a road*/
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(true);
        when(player.getName()).thenReturn("CannotPlace");
        when(edge.canPlaceRoad(any(Player.class))).thenReturn(false);

        buildRoad.execute();

        verify(player).canAfford(any());
        verify(edge, never()).placeRoad(any(Road.class));
        verify(player, never()).build(any(Road.class));
    }

    @Test
    public void executeCanAffordAndValid() {
        /* Player has everything they need to place a road */
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(true);
        when(player.getName()).thenReturn("CanPlace");
        when(edge.canPlaceRoad(any(Player.class))).thenReturn(true);

        buildRoad.execute();

        verify(player).canAfford(any());
        verify(edge).placeRoad(any(Road.class));
        verify(player).build(any(Road.class));
    }
}