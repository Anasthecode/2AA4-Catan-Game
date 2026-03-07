package com.team22.catan.game;

import com.team22.catan.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class ComputerPlayerTest {
    private static final int TEST_SEED = 5000;

    @Mock
    private Board board;

    @Mock
    private Dice dice;

    @Mock private Random random;

    private ComputerPlayer computerPlayer;
    private Game game;
    private List<Player> playerList;

    @BeforeEach
    public void setUp() {
        computerPlayer = new ComputerPlayer("Test", random);

        playerList = new ArrayList<>();
        playerList.add(computerPlayer);

        game = new Game(TEST_SEED, board, playerList, dice);

        when(random.nextInt(anyInt())).thenReturn(0);
    }


    @Test
    void onTurn() {

    }
}