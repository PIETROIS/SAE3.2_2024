package src.view;

import src.entity.Cell;
import src.entity.Colors;
import src.controller.CellMouseListener;

import javax.swing.*;
import java.awt.*;

/**
 * Vue de la grille contenant les cellules.
 */
public class GridView extends JPanel {

    private final int size = 9;
    private JPanel[][] panels;

    /**
     * Construit une nouvelle instance de la vue de la grille.
     * 
     * @param cells        La grille de cellules à afficher.
     * @param editCellView La vue de l'éditeur de cellule.
     */
    public GridView(Cell[][] cells, EditCellView editCellView) {
        String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
        panels = new JPanel[size][size];
        this.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                panels[i][j] = new JPanel();
                GridBagLayout layout = new GridBagLayout();
                panels[i][j].setLayout(layout);
                panels[i][j].add(cells[i][j], cellConstraint());
                panels[i][j].setBackground(Colors.chooseColor(cells[i][j].getStatus()));
                panels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                panels[i][j].setName(letters[j] + (i + 1));
                panels[i][j].addMouseListener(new CellMouseListener(editCellView)); // Passage de la référence à
                                                                                    // EditCellView
                this.add(panels[i][j]);
            }
        }
    }

    /**
     * Définit les contraintes pour l'affichage des cellules dans la grille.
     * 
     * @return Les contraintes.
     */
    public GridBagConstraints cellConstraint() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        return gbc;
    }
}
