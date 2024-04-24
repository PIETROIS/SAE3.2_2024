package src.entity;

/**
 * L'énumération Status représente les différents états possibles d'une cellule.
 */
public enum Status {
    EMPTY("EMPTY"), CALCULABLE("CALCULABLE"), UNCALCULABLE("UNCALCULABLE"), INCORRECT("INCORRECT");

    private String status; // Le statut de la cellule

    /**
     * Constructeur de l'énumération Status.
     *
     * @param status Le statut de la cellule.
     */
    Status(String status) {
        this.status = status;
    }

    /**
     * Retourne la représentation textuelle du statut.
     *
     * @return Le statut sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        return this.status;
    }
}
