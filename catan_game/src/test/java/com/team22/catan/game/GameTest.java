package com.team22.catan.game;

import com.team22.catan.board.Board;
import com.team22.catan.board.CatanBoard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 *
 * These tests only focus on the simpler public methods for the sake of understanding and simplicity:
 * - constructor behavior
 * - getState()
 * - getPlayers()
 * - getBoard()
 * - rollDice()
 * - toString()
 *
 * We avoid testing the more complicated full gameplay loop here because they are unnecessarily long
 */
public class GameTest {

    /**
     * A very small test version of Player.
     *
     * Since Player is abstract, we must create a child class, and I do not want to use ComputerPlayer
     * so we get a simpler understanding using a base class, so we can actually make Player objects for testing.
     */
    private static class TestPlayer extends Player {
        public TestPlayer(String name) {
            super(name);
        }

        @Override
        public void onTurn(Game game) {
            // Do nothing.
            // This is fine for simple Game tests.
        }
    }

    /**
     * A predictable Random for testing.
     *
     * Dice uses rng.nextInt(6) + 1.
     * So if nextInt always returns 0, each die becomes 1.
     * Two dice -> total roll = 2.
     *
     * This helps us test rollDice() in a controlled way.
     */
    private static class FixedRandom extends Random {
        @Override
        public int nextInt(int bound) {
            return 0; // always gives the smallest possible value
        }
    }

    /**
     * Helper method to create a small list of players.
     */
    private List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new TestPlayer("Sam"));
        players.add(new TestPlayer("Scott"));
        return players;
    }

    /**
     * Helper method to create a standard board using the correct constructor.
     */
    private Board createBoard() {
        return new CatanBoard(
                CatanSettings.STANDARD_BOARD_LAYOUT,
                CatanSettings.TOKEN_LAYOUT
        );
    }

    /**
     * Test that a new Game starts in the SETUP state.
     */
    @Test
    public void gameStartsInSetupState() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);

        assertEquals(GameState.SETUP, game.getState());
    }

    /**
     * Test that getBoard() returns the exact same board object
     * that was given to the constructor.
     */
    @Test
    public void getBoardReturnsTheOriginalBoard() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);

        assertSame(board, game.getBoard());
    }

    /**
     * Test that getPlayers() returns the correct number of players.
     */
    @Test
    public void getPlayersReturnsCorrectListSize() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);

        assertEquals(2, game.getPlayers().size());
    }

    /**
     * Test that getPlayers() returns the same players in the same order.
     */
    @Test
    public void getPlayersReturnsCorrectPlayersInOrder() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);
        List<Player> returnedPlayers = game.getPlayers();

        assertEquals("Sam", returnedPlayers.get(0).getName());
        assertEquals("Scott", returnedPlayers.get(1).getName());
    }

    /**
     * Test that getPlayers() returns a copy of the list, not the original list itself.
     *
     * If we change the returned list, it should NOT affect the Game's real player list.
     */
    @Test
    public void getPlayersReturnsCopyOfListNotOriginalList() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);

        List<Player> returnedPlayers = game.getPlayers();
        returnedPlayers.clear(); // only clears the copy

        // The internal list inside Game should still be unchanged
        assertEquals(2, game.getPlayers().size());
    }

    /**
     * Test that rollDice() can return the minimum possible value.
     *
     * Because our FixedRandom always returns 0:
     * each die becomes (0 + 1) = 1
     * so two dice should total 2
     *
     * This is a nice boundary-style test.
     */
    @Test
    public void rollDiceCanReturnMinimumValueTwo() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new FixedRandom());

        Game game = new Game(10, board, players, dice);

        assertEquals(2, game.rollDice());
    }

    /**
     * Test that rollDice() always stays in the valid Catan range: 2 to 12.
     */
    @Test
    public void rollDiceAlwaysReturnsValueBetweenTwoAndTwelve() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);

        for (int i = 0; i < 100; i++) {
            int roll = game.rollDice();
            assertTrue(roll >= 2 && roll <= 12);
        }
    }

    /**
     * Test that Game.toString() matches Board.toString().
     *
     * In the Game class, toString() simply returns board.toString().
     */
    @Test
    public void toStringMatchesBoardToString() {
        Board board = createBoard();
        List<Player> players = createPlayers();
        Dice dice = new Dice(new Random());

        Game game = new Game(10, board, players, dice);

        assertEquals(board.toString(), game.toString());
    }
}