package yahtzee;

import java.util.Collection;

public class FullHouseCombination extends Combination {

    /**
     * Crée une figure (full) avec les valeurs actuelles des dés spécifiés
     * @param dice les dés
     * @throws IllegalArgumentException si la collection comprend plus ou moins que 5 éléments
     */
    public FullHouseCombination(Collection<Dice> dice) {
        super(dice);
    }

    private boolean isFullHouse() {
        // cette méthode se base sur le fait que le vecteur de valeurs est trié
        return values.get(0).equals(values.get(1))
                && values.get(1).equals(values.get(2))
                && values.get(3).equals(values.get(4));
    }

    /**
     * Retourne le score associé à cette combinaison pour les valeurs de dés : 25 si les dés forment un full, 0 sinon
     * Un full est constitué de trois dés qui affichent la même valeur, et 2 autres dés qui affichent une même valeur,
     * différente de la première.
     * @return le score associé à cette combinaison
     */
    @Override
    public int getScore() {
        return isFullHouse() ? 25 : 0;
    }
}
