package src.entity;

import javax.swing.*;
import java.awt.*;

/**
 * La classe Cell représente une cellule dans une feuille de calcul.
 * Elle hérite de JLabel pour afficher le contenu de la cellule.
 */
public class Cell extends JLabel {
    private final int x;
    private final int y;
    private String formula;
    private Status status;
    private Node formulaAST;

    /**
     * Constructeur de la classe Cell.
     *
     * @param x       Position horizontale de la cellule.
     * @param y       Position verticale de la cellule.
     * @param formula La formule associée à la cellule.
     * @param status  Le statut de la cellule.
     */
    public Cell(int x, int y, String formula, Status status) {
        this.x = x;
        this.y = y;
        this.formula = formula;
        this.status = status;
        // Initialisation de l'AST à null
        this.formulaAST = null;
        updateText();

        this.setPreferredSize(new Dimension(80, 40));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Met à jour le texte affiché dans la cellule en fonction de son statut.
     */
    private void updateText() {
        if (this.status == Status.CALCULABLE) {
        } else if (this.status == Status.INCORRECT) {
            this.setText("ERR");
        } else {
            this.setText("");
        }
    }

    /**
     * Renvoie la position horizontale de la cellule.
     *
     * @return La position horizontale de la cellule.
     */
    @Override
    public int getX() {
        return this.x;
    }

    /**
     * Renvoie la position verticale de la cellule.
     *
     * @return La position verticale de la cellule.
     */
    @Override
    public int getY() {
        return this.y;
    }

    /**
     * Renvoie la formule associée à la cellule.
     *
     * @return La formule associée à la cellule.
     */
    public String getFormula() {
        return this.formula;
    }

    /**
     * Remplace la formule actuelle de la cellule par une nouvelle formule.
     *
     * @param newFormula La nouvelle formule à assigner à la cellule.
     * @return La formule mise à jour de la cellule.
     */
    public String replaceFormula(String newFormula) {
        this.formula = newFormula;
        this.formulaAST = null;
        return this.formula;
    }

    /**
     * Renvoie le statut de la cellule.
     *
     * @return Le statut de la cellule.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Définit le statut de la cellule et met à jour son texte en conséquence.
     *
     * @param status Le nouveau statut à assigner à la cellule.
     */
    public void setStatus(Status status) {
        this.status = status;
        updateText();
    }

    /**
     * Renvoie l'arbre de syntaxe abstraite (AST) associé à la formule de la
     * cellule.
     *
     * @return L'AST associé à la formule de la cellule.
     */
    public Node getFormulaAST() {
        return formulaAST;
    }

    /**
     * Définit l'arbre de syntaxe abstraite (AST) associé à la formule de la
     * cellule.
     *
     * @param formulaAST Le nouvel AST à assigner à la formule de la cellule.
     */
    public void setFormulaAST(Node formulaAST) {
        this.formulaAST = formulaAST;
    }
}
