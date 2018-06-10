package math.simulation.common;

/**
 * DimensionErrorExceptions is thrown iff the dimension of two represented vectors or points is not equal
 */
public class DimensionErrorException extends Exception {
    /**
     * create default
     */
    public DimensionErrorException() {
        super("dimension error");
    }

    /**
     * create with custom message
     */
    public DimensionErrorException(String message) {
        super(message);
    }
}
