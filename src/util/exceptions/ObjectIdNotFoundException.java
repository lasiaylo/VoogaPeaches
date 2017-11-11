package util.exceptions;

/**
 * An Exception meant to handle the error that occurs when
 * trying to save an object to the database that does not
 * possess an id instance variable marked with the @Expose
 * annotation
 */
public class ObjectIdNotFoundException extends Exception {
    public String getMessage() {
        return "The object being added to the database doesn't have an id instance variable marked with the @Expose annotation.";
    }
}
