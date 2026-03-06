package com.team22.catan.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;


class DiceTest {
    @Test
    public void testDiceRollWithSeed() {
        /* This class is mainly for computer players, its role is to test if the results can be calculated consistently provided we have a seed*/
        long seed = 555555;
        Random rng = new Random(seed);
        Dice testDice = new Dice(rng);
        int diceCount = 5;

        int result = testDice.rollDice(diceCount);
        assertEquals(20, result);
    }

    @Test
    public void testDiceRollWithoutSeed() {
        Random rng = new Random();
        Dice testDice = new Dice(rng);
        int diceCount = 5;
        int result;
        boolean isInRange;
        for (int i = 0; i < 20; i++) {
            result = testDice.rollDice(diceCount);
            isInRange = (0 <= result && result <= 6 * diceCount);
            assertTrue(isInRange);
        }
    }
}