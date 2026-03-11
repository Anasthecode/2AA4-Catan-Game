package com.team22.catan.actions;

import com.team22.catan.board.Board;
import com.team22.catan.board.NodePosition;
import com.team22.catan.game.Game;
import com.team22.catan.game.GameState;
import com.team22.catan.game.Player;
import com.team22.catan.structures.Settlement;
import com.team22.catan.structures.Structure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BuildSettlementTest {

    @Mock
    private Player player;

    @Mock
    private Board board;

    @Mock
    private Settlement settlement;

    @Mock
    private NodePosition nodePosition;

    @Mock
    private Game game;

    private BuildSettlement buildSettlement;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks so that they aren't null

        when(game.getBoard()).thenReturn(board); // Get the board

        buildSettlement = new BuildSettlement(player, game, nodePosition);
    }
    @Test
    public void executeStateSetupAndCannotPlace() {
        when(game.getState()).thenReturn(GameState.SETUP);
        when(board.canPlaceSettlementAt(nodePosition, player, game.getState())).thenReturn(false);
        when(player.getName()).thenReturn("CannotPlaceAtSetup");

        buildSettlement.execute();

        verify(player, never()).addVictoryPoints(1);
        verify(board, never()).placeSettlementAt(nodePosition, settlement, game.getState());
    }


    @Test
    public void executeStateSetupAndCanPlace() {
        when(game.getState()).thenReturn(GameState.SETUP);

        // Needed all matchers to go through
        when(board.canPlaceSettlementAt(any(NodePosition.class), any(Player.class), any(GameState.class))).thenReturn(true);

        when(player.getName()).thenReturn("PlacedAtSetup");

        buildSettlement.execute();

        verify(player, times(1)).addVictoryPoints(1);
        verify(board, times(1)).placeSettlementAt(any(NodePosition.class), any(Settlement.class), any(GameState.class));
    }

    @Test
    public void executeCannotAfford() {
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(false);
        when(player.getName()).thenReturn("CannotAfford");

        buildSettlement.execute();

        verify(player, never()).addVictoryPoints(1);
        verify(player, never()).build(any(Structure.class));
    }

    @Test
    public void executeCanAffordButInvalid() {
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(true);
        when(player.getName()).thenReturn("CanAffordButNotPlace");

        buildSettlement.execute();

        verify(player, never()).addVictoryPoints(1);
        verify(player, never()).build(any(Structure.class));
    }


    @Test
    public void executePlacesSettlementPlaying() {
        when(game.getState()).thenReturn(GameState.PLAYING);
        when(player.canAfford(any())).thenReturn(true);
        when(board.canPlaceSettlementAt(nodePosition, player, game.getState())).thenReturn(true);
        when(player.getName()).thenReturn("WillPlaceAtNonSetup");

        buildSettlement.execute();

        verify(player, times(1)).addVictoryPoints(1);
        verify(player, times(1)).build(any(Structure.class));
    }
}