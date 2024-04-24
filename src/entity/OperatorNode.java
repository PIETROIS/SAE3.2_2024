package src.entity;

import src.exception.UncalculableFormulaException;

/**
 * La classe OperatorNode représente un nœud dans l'arbre syntaxique contenant
 * un opérateur.
 */
public class OperatorNode extends Node {
    private Node left;
    private Node right;
    private char operator;

    /**
     * Constructeur de la classe OperatorNode.
     *
     * @param operator L'opérateur.
     * @param left     Le nœud de l'opérande gauche.
     * @param right    Le nœud de l'opérande droite.
     */
    public OperatorNode(char operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /**
     * Évalue le nœud et retourne le résultat de l'opération entre les opérandes
     * gauche et droite.
     *
     * @return Le résultat de l'opération.
     * @throws UncalculableFormulaException Si une erreur se produit lors de
     *                                      l'évaluation de l'opération.
     */
    @Override
    public double evaluate() throws UncalculableFormulaException {
        switch (operator) {
            case '+':
                return left.evaluate() + right.evaluate();
            case '-':
                return left.evaluate() - right.evaluate();
            case '*':
                return left.evaluate() * right.evaluate();
            case '/':
                if (right.evaluate() == 0) {
                    throw new UncalculableFormulaException("Division by zero.");
                }
                return left.evaluate() / right.evaluate();
            default:
                throw new UnsupportedOperationException("Unsupported operator: " + operator);
        }
    }

    /**
     * Renvoie le nœud fils gauche de ce nœud.
     * <p>
     * Cette méthode permet d'accéder au nœud enfant situé à gauche du nœud courant
     * dans l'arbre. Si ce nœud est une feuille ou n'a pas de nœud enfant gauche,
     * cette méthode renverra {@code null}.
     * </p>
     * 
     * @return Le nœud enfant gauche de ce nœud, ou {@code null} s'il n'existe pas.
     */
    public Node getLeft() {
        return this.left;
    }

    /**
     * Renvoie le nœud fils droit de ce nœud.
     * <p>
     * Cette méthode permet d'accéder au nœud enfant situé à droite du nœud courant
     * dans l'arbre. Si ce nœud est une feuille ou n'a pas de nœud enfant droit,
     * cette méthode renverra {@code null}.
     * </p>
     * 
     * @return Le nœud enfant droit de ce nœud, ou {@code null} s'il n'existe pas.
     */
    public Node getRight() {
        return this.right;
    }

}
