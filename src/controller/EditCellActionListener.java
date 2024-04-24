package src.controller;

import src.view.EditCellView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Écouteur d'action pour gérer les événements d'action sur la vue d'édition de
 * cellule.
 * Cette classe est responsable de la mise à jour de la formule de la cellule
 * lorsque l'action correspondante est déclenchée.
 */
public class EditCellActionListener implements ActionListener {

    private EditCellView editCellView;

    /**
     * Construit un nouveau EditCellActionListener.
     * 
     * @param editCellView La vue d'édition de cellule associée à l'écouteur.
     */
    public EditCellActionListener(EditCellView editCellView) {
        this.editCellView = editCellView;
    }

    /**
     * Appelé lorsqu'un événement d'action est déclenché.
     * 
     * @param e L'événement ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        editCellView.updateCellFormula();
    }
}
