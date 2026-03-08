package com.team22.catan.game;

import com.team22.catan.board.Board;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayerTest {
    private static final int TEST_SEED = 5000;

    @Mock
    private Board board;

    @Mock
    private Dice dice;

    @Mock private Random random;

    private ComputerPlayer computerPlayer;
    private List<Player> playerList;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        computerPlayer = new ComputerPlayer("Test", random);

        playerList = new ArrayList<>();
        playerList.add(computerPlayer);

        Game game = new Game(TEST_SEED, board, playerList, dice);

        when(random.nextInt(anyInt())).thenReturn(0);
    }


    @Test
    public void onTurn() {

    }
}