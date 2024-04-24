package src.exception;

/**
 * Une exception levée lorsqu'une formule est incorrecte.
 */
public class IncorrectFormulaException extends Exception {
    /**
     * Constructeur de l'exception IncorrectFormulaException.
     *
     * @param message Le message d'erreur associé à l'exception.
     */
    public IncorrectFormulaException(String message) {
        super(message);
    }
}
