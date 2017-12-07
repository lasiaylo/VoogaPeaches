package util.exceptions;

/**
 * An Exception meant to handle the case where an ObjectFactory
 * is unable to read in the JSON file describing the blueprint
 * for an object.
 *
 * @author Walker Willetts
 */
public class ObjectBlueprintNotFoundException extends Exception {
    @Override
	public String getMessage(){
        return "Blueprint for the entity object could not be found.";
    }
}
