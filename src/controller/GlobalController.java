package src.controller;

import src.view.GlobalView;
import src.entity.Cell;
import src.model.GridModel;

/**
 * Contrôleur global de l'application.
 */
/**
 * Contrôleur global de l'application.
 */
public class GlobalController {

    /** Matrice de cellules représentant la feuille de calcul. */
    Cell[][] cells;

    /**
     * Constructeur de GlobalController.
     * Initialise la grille de cellules et affiche la vue globale.
     */
    public GlobalController() {
        GridModel model = new GridModel(9, 9);
        cells = model.createSpreadSheet();
        GlobalView view = new GlobalView(cells, 9);

    }

}
