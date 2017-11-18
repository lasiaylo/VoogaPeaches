package util.exceptions;

/**
 * An Exception thrown when groovy instantiation fails
 */
public class GroovyInstantiationException extends Exception {
    /**
     * Creates a new GroovyInstantiationException
     */
    public GroovyInstantiationException() {
        super();
    }

    /**
     * Creates a new GroovyInstantiationException with the param message
     * @param message   message to be attached
     */
    public GroovyInstantiationException(String message) {
        super(message);
    }
}
