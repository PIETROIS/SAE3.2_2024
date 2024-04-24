package src.entity;

import java.awt.Color;

/**
 * La classe Colors fournit des méthodes pour choisir la couleur d'arrière-plan
 * en fonction du statut d'une cellule.
 */
public class Colors {

    /**
     * Choisissez une couleur d'arrière-plan en fonction du statut de la cellule.
     *
     * @param status Le statut de la cellule.
     * @return La couleur d'arrière-plan correspondante.
     */
    public static Color chooseColor(Status status) {
        if (status == Status.EMPTY) {
            return Color.LIGHT_GRAY;
        } else if (status == Status.CALCULABLE) {
            return new Color(144, 238, 144);
        } else if (status == Status.INCORRECT) {
            return Color.RED;
        } else if (status == Status.UNCALCULABLE) {
            return new Color(235, 160, 20);
        } else {
            return Color.BLACK;
        }
    }
}
