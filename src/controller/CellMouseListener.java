package src.controller;

import src.entity.Cell;
import src.view.GlobalView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import src.view.EditCellView;

/**
 * Écouteur de la souris pour gérer les événements de la souris sur les
 * cellules.
 * Cette classe est responsable de la mise à jour de la vue globale en fonction
 * de la cellule sélectionnée.
 */
public class CellMouseListener implements MouseListener {

    private EditCellView editCellView;

    /**
     * Construit un nouveau CellMouseListener.
     * 
     * @param editCellView La vue d'édition de cellule associée à l'écouteur.
     */
    public CellMouseListener(EditCellView editCellView) {
        this.editCellView = editCellView;
    }

    /**
     * Appelé lorsque la souris est cliquée (mais pas relâchée) sur la cellule.
     * 
     * @param me L'événement MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent me) {
        JPanel cellPanel = (JPanel) me.getSource();
        Cell clickedcell = (Cell) cellPanel.getComponent(0);
        editCellView.setSelectedCell(clickedcell);
        Container parent = cellPanel.getParent();
        while (!(parent instanceof GlobalView) && parent != null) {
            parent = parent.getParent();
        }
        GlobalView frame = (GlobalView) parent;
        frame.setCurrentCell(clickedcell);
        frame.updateView();
    }

    /**
     * Les méthodes suivantes ne sont pas utilisées dans cette implémentation de
     * l'interface MouseListener.
     */
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
