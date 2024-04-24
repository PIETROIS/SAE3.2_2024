package src.exception;

/**
 * Une exception levée lorsqu'une formule est vide.
 */
public class EmptyFormulaException extends Exception {
    /**
     * Constructeur de l'exception EmptyFormulaException.
     *
     * @param message Le message d'erreur associé à l'exception.
     */
    public EmptyFormulaException(String message) {
        super(message);
    }
}
