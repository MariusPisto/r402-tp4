package yahtzee;

public interface PlayerI {

    /**
     * Ajoute une combinaison à la feuille de score du joueur
     * @param combination la combinaison
     * @throws IllegalStateException si une combinaison du même type est déjà présente dans la feuille de score
     */
    void addCombinationToScoreSheet(Combination combination) throws IllegalStateException;

    /**
     * Retourne le score actuel du joueur
     * @return le score actuel du joueur
     */
    int getScore();

}
