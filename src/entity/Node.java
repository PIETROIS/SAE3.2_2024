package src.entity;

import src.exception.UncalculableFormulaException;

/**
 * La classe abstraite Node représente un nœud dans l'arbre syntaxique d'une
 * formule.
 * Elle fournit une méthode abstraite pour évaluer le nœud.
 */
public abstract class Node {

    /**
     * Évalue le nœud et retourne le résultat.
     *
     * @return Le résultat de l'évaluation du nœud.
     * @throws UncalculableFormulaException Si une erreur se produit lors de
     *                                      l'évaluation.
     */
    public abstract double evaluate() throws UncalculableFormulaException;
}
