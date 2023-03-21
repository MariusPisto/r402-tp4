package yahtzee;

import java.util.Collection;

public class SixesCombination extends Combination {

    /**
     * Crée une figure (six) avec les valeurs actuelles des dés spécifiés
     * @param dice les dés
     * @throws IllegalArgumentException si la collection comprend plus ou moins que 5 éléments
     */
    public SixesCombination(Collection<Dice> dice) {
        super(dice);
    }

    /**
     * Retourne le score associé à cette combinaison pour les valeurs de dés : la somme des dés qui affichent la valeur 6
     * @return le score associé à cette combinaison
     */
    @Override
    public int getScore() {
        return (int) values.stream().filter(v -> v == 6).count();
    }
}
