package yahtzee;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Un tour de jeu d'un joueur.
 */
public class Turn {

    private static final int MAX_NUMBER_OF_ROLLS = 3;

    /**
     * Le joueur
     */
    private PlayerI player;

    /**
     * Les dés
     */
    private Vector<Dice> dice = new Vector<>(Combination.NUMBER_OF_DICE);

    /**
     * Le nombre de relances autorisées pour ce tour.
     */
    private int remainingRolls = MAX_NUMBER_OF_ROLLS;

    /**
     * Les dés à relancer. Cette sélection est vide initialement, et après chaque lancer.
     */
    private Set<Dice> selectedForRoll = new HashSet<>();

    /**
     * Indique si le tour est fini et la combinaison résultante ajoutée à la feuille de score du joueur.
     */
    private boolean isEnded = false;

    /**
     * Crée un tour de jeu pour le joueur spécifié.
     * Le premier lancer de dés est effectué lors de l'appel à ce constructeur.
     * La selection de dés à relancer est initialement vide.
     * @param player le joueur
     */
    public Turn(PlayerI player) {
        this.player = player;
        for (int i = 0; i < Combination.NUMBER_OF_DICE; i++) {
            dice.add(new Dice());
        }
        for (Dice d: dice) {
            d.roll();
        }
    }

    /**
     * Retourne le joueur dont c'est le tour.
     * @return le joueur
     */
    public PlayerI getPlayer() {
        return player;
    }

    /**
     * Retourne la valeur du dé spécifié.
     * @param i l'indice du dé
     * @return la valeur du dé
     * @throws IllegalArgumentException si i n'est pas compris entre 0 et 4 (inclus)
     */
    public int getValueOfDice(int i) {
        if (i < 0 || i >= Combination.NUMBER_OF_DICE) {
            throw new IllegalArgumentException("bad index");
        }
        return dice.get(i).getValue();
    }

    /**
     * Ajoute le dé spécifié aux dés à relancer lors du prochain appel à la méthode roll()
     * @param i l'indice du dé
     * @throws IllegalArgumentException si i n'est pas compris entre 0 et 4 (inclus)
     */
    public void selectForRoll(int i) {
        if (i < 0 || i >= Combination.NUMBER_OF_DICE) {
            throw new IllegalArgumentException("bad index");
        }
        selectedForRoll.add(dice.get(i));
    }

    /**
     * Retire le dé spécifié des dés à relancer lors du prochain appel à la méthode roll()
     * @param i l'indice du dé
     * @throws IllegalArgumentException si i n'est pas compris entre 0 et 4 (inclus)
     */
    public void unselectForRoll(int i) {
        if (i < 0 || i >= Combination.NUMBER_OF_DICE) {
            throw new IllegalArgumentException("bad index");
        }
        selectedForRoll.clear();
    }

    /**
     * Indique si le dé spécifié fait partie des dés à relancer lors du prochain appel à la méthode roll()
     * @param i l'indice du dé
     * @return vrai ssi le dé fait partie des dés à relancer
     * @throws IllegalArgumentException si i n'est pas compris entre 0 et 4 (inclus)
     */
    public boolean isSelectedForRoll(int i) {
        if (i < 0 || i >= Combination.NUMBER_OF_DICE) {
            throw new IllegalArgumentException("bad index");
        }
        return selectedForRoll.contains(dice.get(i));
    }

    /**
     * Relance tous les dés dans la sélection de dés à relancer.
     * Suite à cette méthode, la selection de dés à relancer est vide.
     * @throws IllegalStateException si 3 lancers (y compris le lancer initial) ont déjà été effectués ou si la méthode
     *         end() a déjà été appelé sur cette instance
     */
    public void roll() throws IllegalStateException {
        if (remainingRolls <= 0 || isEnded) { throw new IllegalStateException("no more rolls"); }

        for (Dice d: selectedForRoll) { d.roll(); }
        remainingRolls--;
        selectedForRoll.clear();
    }

    /**
     * Indique si le tour est fini (si la méthode end() a été appelé sur cette instance)
     * @return vrai ssi le tour est fini
     */
    public boolean isEnded() {
        return isEnded;
    }

    /**
     * Finit le tour et ajoute la combinaison à la feuille de score du joueur.
     * @param combinationType le type de combinaison choisie par le joueur
     * @throws IllegalStateException si la méthode a déjà été appelée sur cette instance, ou si une combinaison du type
     *         spécifié est déjà présente sur la feuille de score du joueur
     */
    public void end(Combination.Type combinationType) throws IllegalStateException {
        if (isEnded) { throw new IllegalStateException("turn is ended"); }

        Combination c;
        switch (combinationType) {
            case SIXES -> c = new SixesCombination(dice);
            case FULL_HOUSE -> c = new FullHouseCombination(dice);
            default -> throw new UnsupportedOperationException("not yet implemented");
        }
        player.addCombinationToScoreSheet(c);
        isEnded = true;
    }


}
