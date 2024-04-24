package src.model;

import src.entity.Cell;
import src.entity.Status;

/**
 * Modèle de la grille.
 * Stocke les données de la grille de cellules.
 */
public class GridModel {
    /** La grille de cellules. */
    private Cell[][] spreadsheet;
    /** Le nombre de lignes de la grille. */
    private int i;
    /** Le nombre de colonnes de la grille. */
    private int j;

    /**
     * Constructeur de la classe GridModel.
     * 
     * @param i Nombre de lignes de la grille.
     * @param j Nombre de colonnes de la grille.
     */
    public GridModel(int i, int j) {
        this.i = i;
        this.j = j;
        this.spreadsheet = new Cell[i][j];
    }

    /**
     * Crée une nouvelle feuille de calcul avec des cellules vides.
     * 
     * @return La grille de cellules initialisée.
     */
    public Cell[][] createSpreadSheet() {
        System.out.println("Start");
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                this.spreadsheet[k][l] = new Cell(k, l, "", Status.EMPTY);
            }
        }
        return spreadsheet;
    }

    /**
     * Obtient la grille de cellules.
     * 
     * @return La grille de cellules.
     */
    public Cell[][] getSpreadSheet() {
        return spreadsheet;
    }

    /**
     * Définit la formule d'une cellule à une position donnée.
     * 
     * @param row     L'indice de ligne de la cellule.
     * @param col     L'indice de colonne de la cellule.
     * @param formula La nouvelle formule de la cellule.
     */
    public void setCellFormula(int row, int col, String formula) {
        if (row < 0 || row >= spreadsheet.length || col < 0 || col >= spreadsheet[0].length) {
            throw new IndexOutOfBoundsException("Position de cellule hors limites.");
        }
        Cell cell = spreadsheet[row][col];
        cell.replaceFormula(formula);
        cell.setText(formula);
    }

    /**
     * Obtient la cellule à partir d'une référence donnée.
     * 
     * @param reference La référence de la cellule (par exemple, "A1").
     * @return La cellule correspondante.
     */
    public Cell getCellFromReference(String reference) {
        int row = Character.getNumericValue(reference.charAt(1)) - 1;
        int column = reference.charAt(0) - 'A';
        return spreadsheet[row][column];
    }
}
