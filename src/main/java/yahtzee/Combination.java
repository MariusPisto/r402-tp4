package yahtzee;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

/**
 * Une instance de cette classe représente une figure (combinaison de dés), telle que reportée sur la feuille de score d'un joueur
 * Les classes qui héritent de Combinaison représentent les différents types de figures disponibles (full, yahtzee, uns...)
 */
public abstract class Combination {

    public static final int NUMBER_OF_DICE = 5;

    /**
     * Les types de figures possibles.
     */
    public enum Type {
        ACES, TWOS, THREES, FOURS, FIVES, SIXES,
        THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT, LARGE_STRAIGHT, YAHTZEE, CHANCE
    }

    /**
     * Les valeurs des dés, triées par ordre croissant
     */
    protected Vector<Integer> values = new Vector<>(NUMBER_OF_DICE);

    /**
     * Crée une figure avec les valeurs actuelles des dés spécifiés
     * @param dice les dés
     * @throws IllegalArgumentException si la collection comprend plus ou moins que 5 éléments
     */
    public Combination(Collection<Dice> dice) {
        if (dice.size() != NUMBER_OF_DICE) {
            throw new IllegalArgumentException("bad number of dice");
        }
        for (Dice d: dice) {
            values.add(d.getValue());
        }
        Collections.sort(values);
    }

    /**
     * Retourne la valeur du dé spécifié
     * @param i l'indice du dé
     * @return la valeur du dé
     * @throws IllegalArgumentException si i n'est pas compris entre 0 et 4 (inclus)
     */
    public int getValueOfDice(int i) {
        if (i < 0 || i >= NUMBER_OF_DICE) {
            throw new IllegalArgumentException("bad index");
        }
        return values.get(i);
    }

    /**
     * Retourne le score associé à cette figure pour les valeurs de dés. Si les valeurs ne forment pas une figure du
     * type donné, retourne 0.
     * @return le score associé à cette combinaison
     */
    public abstract int getScore();
}
