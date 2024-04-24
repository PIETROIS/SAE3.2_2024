package src.controller;

import src.entity.Colors;
import src.view.EditCellView;
import src.view.GlobalView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Écouteur de touches pour gérer les événements de frappe sur la cellule en
 * cours d'édition.
 * Cette classe est responsable de la mise à jour de la vue globale en fonction
 * de la touche Entrée pressée.
 */
public class EditCellKeyListener implements KeyListener {

    private EditCellView editCellView;

    /**
     * Construit un nouveau EditCellKeyListener.
     * 
     * @param editCellView La vue d'édition de cellule associée à l'écouteur.
     */
    public EditCellKeyListener(EditCellView editCellView) {
        this.editCellView = editCellView;
    }

    /**
     * Ne pas implémenter cette méthode pour la gestion de la touche Entrée.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Ne pas implémenter cette méthode pour la gestion de la touche Entrée.
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Appelé lorsque la touche est relâchée.
     * Si la touche relâchée est Entrée, la méthode met à jour la formule de la
     * cellule en cours d'édition et
     * actualise la vue globale.
     * 
     * @param e L'événement KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            editCellView.updateCellFormula();
            JTextField field = (JTextField) e.getSource();
            Container parent = field.getParent();
            while (!(parent instanceof GlobalView) && parent != null) {
                parent = parent.getParent();
            }
            GlobalView frame = (GlobalView) parent;
            frame.updateView();
            JPanel panel = (JPanel) editCellView.getCell().getParent();
            panel.setBorder(BorderFactory.createLineBorder(Color.white));
            panel.setBackground(Colors.chooseColor(editCellView.getCell().getStatus()));
        }
    }
}
