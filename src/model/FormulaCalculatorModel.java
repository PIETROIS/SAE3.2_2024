package src.model;

import src.entity.Cell;
import src.entity.Node;
import src.entity.OperandNode;
import src.entity.OperatorNode;
import src.entity.Status;
import src.exception.*;

import java.util.HashSet;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe FormulaCalculatorModel fournit des méthodes pour calculer les
 * formules de cellules,
 * construire des arbres de syntaxe abstraite (AST) pour les formules, et
 * vérifier les références circulaires.
 * Elle contient des méthodes statiques qui opèrent sur des objets de cellule et
 * des tableaux de feuilles de calcul.
 */

public class FormulaCalculatorModel {

    /**
     * Calcule la formule contenue dans une cellule de la feuille de calcul.
     *
     * @param cell        La cellule contenant la formule à calculer.
     * @param spreadsheet La grille de cellules dans laquelle la cellule est située.
     * @return La valeur calculée de la formule.
     * @throws EmptyFormulaException        Si la formule de la cellule est vide.
     * @throws UncalculableFormulaException Si la formule de la cellule ne peut pas
     *                                      être calculée.
     * @throws IncorrectFormulaException    Si la formule de la cellule est
     *                                      incorrecte.
     */
    public static double calculateCellFormula(Cell cell, Cell[][] spreadsheet)
            throws EmptyFormulaException, UncalculableFormulaException, IncorrectFormulaException {
        if (cell == null) {
            throw new IllegalArgumentException("Cell reference cannot be found");
        }
        String formula = cell.getFormula();
        if (formula.isEmpty()) {
            throw new EmptyFormulaException("The cell contains an empty formula.");
        }

        if (!isSyntaxCorrect(formula)) {
            throw new IncorrectFormulaException("The cell contains an incorrect formula.");
        }

        if (containsCircularReference(cell, spreadsheet)) {
            throw new IncorrectFormulaException("Circular reference detected.");
        }

        try {
            if (cell.getFormulaAST() == null) {
                cell.setFormulaAST(buildASTForFormula(cell.getFormula(), spreadsheet));
            }
            return cell.getFormulaAST().evaluate();
        } catch (Exception e) {
            throw new UncalculableFormulaException("The cell contains an incalculable formula.");
        }
    }

    /**
     * Construit l'arbre d'expression pour une formule donnée.
     *
     * @param formula     La formule à partir de laquelle construire l'arbre
     *                    d'expression.
     * @param spreadsheet La grille de cellules dans laquelle la formule est située.
     * @return Le nœud racine de l'arbre d'expression.
     * @throws Exception Si une erreur survient lors de la construction de l'arbre.
     */
    public static Node buildASTForFormula(String formula, Cell[][] spreadsheet) throws Exception {
        Stack<Node> stack = new Stack<>();
        String[] tokens = formula.split("\\s+");
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isNumeric(token)) {
                stack.push(new OperandNode(Double.parseDouble(token)));
            } else if (isCellReference(token)) {
                Cell cell = getCellFromReference(token, spreadsheet);
                if (cell.getFormulaAST() == null) {
                    cell.setFormulaAST(buildASTForFormula(cell.getFormula(), spreadsheet));
                }
                stack.push(cell.getFormulaAST());
            } else if (isOperator(token)) {
                // Pop the stack in the correct order for prefix notation.
                Node firstOperand = stack.pop();
                Node secondOperand = stack.pop();
                stack.push(new OperatorNode(token.charAt(0), firstOperand, secondOperand));
            }
        }
        return stack.pop();
    }

    /**
     * Vérifie la syntaxe d'une formule.
     * Valide si elle n'est pas vide et contient uniquement des nombres, des
     * opérateurs valides (+, -, *, /) et des références de cellules valides (par
     * ex. A1).
     *
     * @param formula La formule à vérifier.
     * @return True si la syntaxe de la formule est correcte, sinon False.
     */
    private static boolean isSyntaxCorrect(String formula) {
        return formula
                .matches("([+-]?\\d*\\.?\\d+|[A-Za-z]+\\d+|[+\\-*/])(\\s+([+-]?\\d*\\.?\\d+|[A-Za-z]+\\d+|[+\\-*/]))*");
    }

    /**
     * Vérifie si une cellule contient une référence circulaire.
     *
     * @param cell        La cellule à vérifier.
     * @param spreadsheet La grille de cellules dans laquelle la cellule est située.
     * @return True si une référence circulaire est détectée, sinon False.
     */
    public static boolean containsCircularReference(Cell cell, Cell[][] spreadsheet) {
        HashSet<String> visitedReferences = new HashSet<>();
        return checkForCircularReference(cell, spreadsheet, visitedReferences);
    }

    /**
     * Vérifie si une cellule contient une référence circulaire.
     *
     * @param cell        La cellule à vérifier pour les références circulaires.
     * @param spreadsheet La feuille de calcul contenant la cellule.
     * @return true si la cellule contient une référence circulaire, sinon false.
     */

    private static boolean checkForCircularReference(Cell cell, Cell[][] spreadsheet,
            HashSet<String> visitedReferences) {
        if (cell == null) {
            return false;
        }
        String formula = cell.getFormula();
        Pattern pattern = Pattern.compile("[A-Za-z]+\\d+");
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            String reference = matcher.group();
            if (visitedReferences.contains(reference)) {
                // Référence circulaire détectée
                return true;
            } else {
                visitedReferences.add(reference);
                Cell referencedCell = getCellFromReference(reference, spreadsheet);
                if (checkForCircularReference(referencedCell, spreadsheet, visitedReferences)) {
                    return true;
                }
                visitedReferences.remove(reference);
            }
        }
        return false;
    }

    /**
     * Récupère la cellule correspondant à une référence de cellule donnée.
     *
     * @param reference   La référence de cellule au format "A1", "B2", etc.
     * @param spreadsheet La grille de cellules dans laquelle la référence est
     *                    située.
     * @return La cellule correspondante ou null si la référence est invalide.
     */
    public static Cell getCellFromReference(String reference, Cell[][] spreadsheet) {
        try {
            int row = Character.getNumericValue(reference.charAt(1)) - 1;
            int column = reference.charAt(0) - 'A';
            if (row >= 0 && row < spreadsheet.length && column >= 0 && column < spreadsheet[row].length) {
                return spreadsheet[row][column];
            } else {
                System.err.println("Référence de cellule hors limites: " + reference);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la référence de cellule: " + reference + " Erreur: "
                    + e.getMessage());
            return null;
        }
    }

    /**
     * Calcule une expression au format préfixe.
     *
     * @param prefix      L'expression au format préfixe à évaluer.
     * @param spreadsheet La grille de cellules dans laquelle l'expression est
     *                    située.
     * @return La valeur calculée de l'expression.
     * @throws EmptyFormulaException        Si la formule est vide.
     * @throws UncalculableFormulaException Si la formule ne peut pas être calculée.
     * @throws IncorrectFormulaException    Si la formule est incorrecte.
     */
    public static double calculatePrefix(String prefix, Cell[][] spreadsheet)
            throws EmptyFormulaException, UncalculableFormulaException, IncorrectFormulaException {
        Stack<Node> stack = new Stack<>();
        String[] tokens = prefix.split("\\s+");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(new OperandNode(Double.parseDouble(token)));
            } else if (token.matches("[A-Za-z]+\\d+")) {
                Cell cell = getCellFromReference(token, spreadsheet);
                double cellValue = FormulaCalculatorModel.calculateCellFormula(cell, spreadsheet);
                stack.push(new OperandNode(cellValue));
            } else {
                Node left = stack.pop();
                Node right = stack.pop();
                stack.push(new OperatorNode(token.charAt(0), left, right));
            }
        }

        Node root = stack.pop();
        return root.evaluate();
    }

    /**
     * Vérifie si un token est numérique.
     *
     * @param token Le token à vérifier.
     * @return True si le token est numérique, sinon False.
     */
    private static boolean isNumeric(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Vérifie si un token est une référence de cellule.
     *
     * @param token Le token à vérifier.
     * @return True si le token est une référence de cellule, sinon False.
     */
    private static boolean isCellReference(String token) {
        return token.matches("[A-Za-z]+\\d+");
    }

    /**
     * Vérifie si un token est un opérateur.
     *
     * @param token Le token à vérifier.
     * @return True si le token est un opérateur, sinon False.
     */
    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    /**
     * Met à jour la formule d'une cellule spécifiée et récursivement toutes les
     * cellules qui en dépendent.
     *
     * @param cell        La cellule initialement mise à jour.
     * @param spreadsheet La grille de cellules représentant le tableau.
     */
    public static void updateResult(Cell cell, Cell[][] spreadsheet) {
        HashSet<Cell> visitedCells = new HashSet<>(); // Pour éviter les mises à jour infinies dans les références
                                                      // circulaires
        updateResultRecursive(cell, spreadsheet, visitedCells, cell.getStatus());
    }

    /**
     * Fonction récursive pour mettre à jour une cellule et toutes ses dépendances.
     *
     * @param cell         La cellule à mettre à jour.
     * @param spreadsheet  La grille de cellules représentant le tableau.
     * @param visitedCells Ensemble des cellules déjà visitées pour éviter les
     *                     boucles infinies.
     */
    private static void updateResultRecursive(Cell cell, Cell[][] spreadsheet, HashSet<Cell> visitedCells,
            Status status) {
        if (visitedCells.contains(cell)) {
            return; // Évite la mise à jour récursive des cellules déjà visitées.
        }
        visitedCells.add(cell);

        if (status == Status.UNCALCULABLE) {
            cell.setText("ERR");
            cell.setStatus(Status.UNCALCULABLE);
        } else {
            try {
                double result = calculateCellFormula(cell, spreadsheet);
                cell.setText(String.valueOf(result));
                cell.setFormulaAST(buildASTForFormula(cell.getFormula(), spreadsheet));
                cell.setStatus(Status.CALCULABLE);
            } catch (EmptyFormulaException e) {
                cell.setText("");
                cell.setStatus(Status.EMPTY);

            } catch (IncorrectFormulaException e) {
                cell.setText("ERR");
                cell.setStatus(Status.INCORRECT);

            } catch (UncalculableFormulaException e) {
                cell.setText("ERR");
                cell.setStatus(Status.UNCALCULABLE);

            } catch (Exception e) {
                cell.setText("ERR");
                cell.setStatus(Status.UNCALCULABLE);

                System.err.println("Erreur inattendue lors du recalcul : " + e.getMessage());
            }
        }

        // Pour chaque cellule dans le tableau, vérifie si elle référence la cellule
        // mise à jour et déclenche la mise à jour récursive.
        for (int i = 0; i < spreadsheet.length; i++) {
            for (int j = 0; j < spreadsheet[i].length; j++) {
                Cell currentCell = spreadsheet[i][j];
                if (currentCell != null && containsReferenceToCell(currentCell, cell, spreadsheet)) {
                    updateResultRecursive(currentCell, spreadsheet, visitedCells, cell.getStatus());
                }
            }
        }
    }

    /**
     * Vérifie si une cellule fait référence à une autre cellule spécifiée.
     *
     * @param sourceCell  La cellule source contenant une formule qui pourrait faire
     *                    référence à une autre cellule.
     * @param targetCell  La cellule cible dont on cherche à savoir si elle est
     *                    référencée.
     * @param spreadsheet La grille de cellules de la feuille de calcul.
     * @return true si la cellule source fait référence à la cellule cible, sinon
     *         false.
     */
    private static boolean containsReferenceToCell(Cell sourceCell, Cell targetCell, Cell[][] spreadsheet) {
        String formula = sourceCell.getFormula();
        Pattern pattern = Pattern.compile("[A-Za-z]+\\d+");
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            String reference = matcher.group();
            Cell referencedCell = getCellFromReference(reference, spreadsheet);
            if (referencedCell == targetCell) {
                return true;
            }
        }
        return false;
    }

}
