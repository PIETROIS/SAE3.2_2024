package src.entity;

import src.exception.UncalculableFormulaException;

/**
 * Représente un nœud opérande dans un arbre syntaxique pour l'évaluation de
 * formules.
 * Cette classe étend {@code Node} pour stocker soit une valeur numérique
 * directe, soit une référence à une cellule
 * dont la valeur est déterminée en évaluant la formule de la cellule
 * référencée.
 */
public class OperandNode extends Node {
    /**
     * La valeur numérique de l'opérande si le nœud n'est pas une référence de
     * cellule.
     */
    private double value;

    /**
     * Référence à une cellule si le nœud représente une référence plutôt qu'une
     * valeur numérique directe.
     * Si {@code null}, le nœud est considéré comme contenant une valeur numérique
     * directe.
     */
    private Cell cellReference;

    /**
     * Construit un {@code OperandNode} avec une valeur numérique.
     * 
     * @param value La valeur numérique de l'opérande.
     */
    public OperandNode(double value) {
        this.value = value;
        this.cellReference = null;
    }

    /**
     * Évalue le nœud en retournant soit la valeur numérique directe, soit en
     * évaluant la formule de la cellule référencée.
     * 
     * @return La valeur numérique représentée par cet opérande.
     * @throws UncalculableFormulaException Si la formule de la cellule référencée
     *                                      ne peut pas être calculée.
     */
    @Override
    public double evaluate() throws UncalculableFormulaException {
        return isReference() ? cellReference.getFormulaAST().evaluate() : value;
    }

    /**
     * Détermine si le nœud est une référence à une cellule plutôt qu'une valeur
     * numérique directe.
     * 
     * @return {@code true} si ce nœud est une référence à une cellule, sinon
     *         {@code false}.
     */
    public boolean isReference() {
        return this.cellReference != null;
    }

    /**
     * Obtient la cellule référencée par ce nœud, s'il s'agit d'une référence de
     * cellule.
     * 
     * @return La cellule référencée, ou {@code null} si ce nœud contient une valeur
     *         numérique directe.
     */
    public Cell getCell() {
        return this.cellReference;
    }

    /**
     * Définit la valeur numérique de cet opérande.
     * Cette méthode ne devrait être utilisée que si le nœud n'est pas une référence
     * de cellule.
     * 
     * @param value La nouvelle valeur numérique de l'opérande.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Définit la référence de cellule pour ce nœud.
     * Après appel de cette méthode, le nœud agira comme une référence de cellule
     * plutôt qu'une valeur numérique directe.
     * 
     * @param cellReference La cellule à laquelle ce nœud doit faire référence.
     */
    public void setCellReference(Cell cellReference) {
        this.cellReference = cellReference;
    }
}
