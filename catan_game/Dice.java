import java.util.Random;

/**
 * Represents a die that can be rolled to generate random numbers.
 */
public class Dice {
    private int sides;
    private Random random;

    /**
     * Constructs a Dice with the specified number of sides.
     * 
     * @param sides the number of sides on the die
     */
    public Dice(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    /**
     * Rolls the specified number of dice and returns the results.
     * 
     * @param numDice the number of dice to roll
     * @return array of roll results (1-2 dice)
     */
    public int[] rollDice(int numDice) {
        int[] rolls = new int[numDice];
        for (int i = 0; i < numDice; i++) {
            rolls[i] = random.nextInt(sides) + 1;
        }
        return rolls;
    }

    /**
     * Gets the number of sides on this die.
     * 
     * @return the number of sides
     */
    public int getSides() {
        return sides;
    }
}
