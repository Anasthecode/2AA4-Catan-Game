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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComputerPlayerTest {
    @Mock
    Random random;
    @Mock
    String string;
    @Mock
    ComputerPlayer player;
    @Mock
    ArrayList<Player> players;
    int turns = 5000;

    @BeforeEach
    public void setUp() {
    }


    @Test
    void onTurn() {

    }
}