package rekkyn.spacetime.utility;

import java.util.Random;

public class MathUtil {
    private static Random rand = new Random();

    public static double NegOneToOne() {
        return (rand.nextDouble() * 2) - 1;
    }
}
