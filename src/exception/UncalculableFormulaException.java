package src.exception;

/**
 * Une exception levée lorsqu'une formule ne peut pas être calculée.
 */
public class UncalculableFormulaException extends Exception {
    /**
     * Constructeur de l'exception UncalculableFormulaException.
     *
     * @param message Le message d'erreur associé à l'exception.
     */
    public UncalculableFormulaException(String message) {
        super(message);
    }
}
