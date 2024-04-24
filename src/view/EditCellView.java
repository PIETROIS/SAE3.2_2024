package src.view;

import src.controller.EditCellKeyListener;
import src.controller.EditCellActionListener;
import src.entity.Cell;
import src.entity.Status;
import src.exception.EmptyFormulaException;
import src.exception.IncorrectFormulaException;
import src.exception.UncalculableFormulaException;
import src.model.FormulaCalculatorModel;

import javax.swing.*;

/**
 * Vue de l'éditeur de cellule permettant de modifier les formules des cellules.
 */
public class EditCellView extends JTextField {

    private Cell[][] cells;
    private Cell selectedCell;

    /**
     * Construit une nouvelle instance de l'éditeur de cellule.
     * 
     * @param cells La grille de cellules à éditer.
     */
    public EditCellView(Cell[][] cells) {
        this.cells = cells;
        setSelectedCell(cells[0][0]);
        addActionListener(new EditCellActionListener(this));
        addKeyListener(new EditCellKeyListener(EditCellView.this));
    }

    /**
     * Récupère la cellule actuellement sélectionnée.
     * 
     * @return La cellule sélectionnée.
     */
    public Cell getCell() {
        return selectedCell;
    }

    /**
     * Définit la cellule sélectionnée pour l'édition.
     * 
     * @param selectedCell La cellule à éditer.
     */
    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
        setText(selectedCell.getFormula());
    }

    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Met à jour la formule de la cellule en fonction de l'entrée de l'utilisateur.
     * Cette méthode est appelée lorsque l'utilisateur appuie sur la touche Entrée
     * après avoir édité la formule.
     */
    public void updateCellFormula() {
        String newFormula = getText();
        if (selectedCell == null) {
            System.err.println("Aucune cellule sélectionnée.");
            return;
        }
        try {
            selectedCell.replaceFormula(newFormula);
            double res = FormulaCalculatorModel.calculateCellFormula(selectedCell, cells);

            // FormulaCalculatorModel.recalculateDependents(selectedCell,this.cells); //
            // recalcul les descendants de l'AST
            System.out.println("Cellule mise à jour : " + res);
            selectedCell.setStatus(Status.CALCULABLE);
            selectedCell.setText(String.valueOf(res));
        } catch (EmptyFormulaException e) {
            System.err.println("Erreur: " + e.getMessage());
            selectedCell.setStatus(Status.EMPTY);
        } catch (UncalculableFormulaException e) {
            System.err.println("Erreur: " + e.getMessage());
            selectedCell.setStatus(Status.UNCALCULABLE);
        } catch (IncorrectFormulaException e) {
            System.err.println("Erreur: " + e.getMessage());
            selectedCell.setStatus(Status.INCORRECT);
        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue: " + e.getMessage());
        }
        FormulaCalculatorModel.updateResult(selectedCell, cells);
    }
}
