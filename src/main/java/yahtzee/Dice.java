package yahtzee;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Une instance de cette classe représente un dé.
 */
public class Dice {

    private int value;

    /**
     * Construit un dé, avec une valeur aléatoire.
     */
    public Dice() {
        roll();
    }

    /**
     * Change la valeur du dé de manière aléatoire.
     */
    public void roll() {
        value = ThreadLocalRandom.current().nextInt(1, 7);
    }

    /**
     * Retourne la valeur actuelle du dé, un entier entre 1 et 6 (inclus).
     * @return la valeur actuelle du dé
     */
    public int getValue() {
        return value;
    }
}
