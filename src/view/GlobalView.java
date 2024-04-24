package src.view;

import src.entity.Cell;
import src.entity.Colors;

import javax.swing.*;
import java.awt.*;

/**
 * Vue globale de l'application.
 */
/**
 * Vue globale de l'application.
 */
public class GlobalView extends JFrame {

    private GridView grid;
    private int size;
    private JLabel label;
    private Cell currentCell;

    /**
     * Constructeur de la vue globale.
     * 
     * @param cells Tableau de cellules représentant la grille.
     * @param size  Taille de la grille.
     */
    public GlobalView(Cell[][] cells, int size) {
        super("Tableur");
        this.size = size;
        this.setSize(new Dimension(700, 600));
        GridBagLayout layout = new GridBagLayout();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(layout);
        currentCell = cells[0][0];
        EditCellView editor = new EditCellView(cells);
        this.grid = new GridView(cells, editor);
        JPanel labelPanel = new JPanel(new GridBagLayout());
        this.label = new JLabel();
        labelPanel.add(this.label, labelConstraint());
        JPanel parent = (JPanel) currentCell.getParent();
        this.label.setText(parent.getName() + " = ");
        this.add(labelPanel, labelConstraint());
        this.add(editor, editorConstraint());

        String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
        JPanel[] panels = new JPanel[19];
        Color green = new Color(144, 238, 144);

        panels[0] = new JPanel();
        panels[0].add(new JLabel(" "));
        panels[0].setBackground(green);
        panels[0].setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(panels[0], legendConstraint(0, 0));

        for (int i = 0; i < 9; i++) {
            panels[i + 1] = new JPanel();
            panels[i + 1].add(new JLabel(letters[i]));
            panels[i + 1].setBackground(green);
            panels[i + 1].setBorder(BorderFactory.createLineBorder(Color.black));
            this.add(panels[i + 1], legendConstraint(i + 1, 0));
        }

        for (int j = 0; j < 9; j++) {
            panels[j + 10] = new JPanel();
            panels[j + 10].add(new JLabel(Integer.toString(j + 1)));
            panels[j + 10].setBackground(green);
            panels[j + 10].setBorder(BorderFactory.createLineBorder(Color.black));
            this.add(panels[j + 10], legendConstraint(0, j + 1));
        }

        this.add(this.grid, cellsConstraint());
        this.setVisible(true);
        this.updateView();
    }

    /**
     * Définit les contraintes pour l'affichage de la grille de cellules.
     * 
     * @return Les contraintes.
     */
    public GridBagConstraints cellsConstraint() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 9;
        gbc.gridheight = 9;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.weightx = 100.0;
        gbc.weighty = 9.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        return gbc;
    }

    /**
     * Définit les contraintes pour l'affichage de l'éditeur de cellule.
     * 
     * @return Les contraintes.
     */
    public GridBagConstraints editorConstraint() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 9;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 10.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        return gbc;
    }

    /**
     * Définit les contraintes pour l'affichage de la légende.
     * 
     * @param x La coordonnée x de la légende.
     * @param y La coordonnée y de la légende.
     * @return Les contraintes.
     */
    public GridBagConstraints legendConstraint(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y + 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        return gbc;
    }

    /**
     * Définit les contraintes pour l'affichage de l'étiquette.
     * 
     * @return Les contraintes.
     */
    public GridBagConstraints labelConstraint() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(1, 1, 1, 1);
        return gbc;
    }

    /**
     * Définit la cellule actuellement sélectionnée.
     * 
     * @param cell La cellule sélectionnée.
     */
    public void setCurrentCell(Cell cell) {
        currentCell = cell;
    }

    /**
     * Met à jour la vue de l'application.
     */

    public void updateView() {
        for (int i = 0; i < this.size * this.size; i++) {
            JPanel panel = (JPanel) this.grid.getComponent(i);
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            Cell cell = (Cell) panel.getComponent(0);
            panel.setBackground(Colors.chooseColor(cell.getStatus()));
        }
        JPanel parent = (JPanel) currentCell.getParent();
        parent.setBorder(BorderFactory.createLineBorder(Color.white));
        label.setText(parent.getName() + " = ");

    }
}
