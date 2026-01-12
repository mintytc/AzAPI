package me.mintytc.azapi.util;

import org.jetbrains.annotations.Contract;

/**
 * @since 1.0.0-R0.1
 *
 */
public class UGUI {

    private UGUI() {
    }

    /**
     * Translate an (x and y) to a slot in an inventory
     * <p>
     *
     * @param x The row of the slot  (1-6)
     * @param y The placement in the row of the slot (1-9)
     *
     * @return The
     * @since 1.0.0-R0.1
     *
     */
    @Contract(pure = true)
    public static int point(int x, int y) {
        return ((x - 1) * 9) + (y - 1);
    }

    /**
     * Translates a digit to an inventory size and prevents typos
     * <p>
     *
     * @param input The digit to translate
     *
     * @since 1.0.0-R0.1
     *
     */
    public static int size(int input) {
        int[] targets = {9, 18, 27, 36, 45, 54};
        if (input <= 0) {
            return 9;
        } else if (input >= 54) {
            return 54;
        } else if (input <= 6) {
            return input * 9;
        }

        int closest = targets[0];
        int minDiff = Math.abs(input - closest);

        for (int i = 1; i < targets.length; i++) {
            int diff = Math.abs(input - targets[i]);
            if (diff < minDiff) {
                minDiff = diff;
                closest = targets[i];
            }
        }

        return closest;
    }
}