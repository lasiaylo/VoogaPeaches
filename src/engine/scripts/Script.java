package engine.scripts;
import java.util.*;
import java.io.File;
import java.io.IOException;
import org.codehaus.groovy.control.CompilationFailedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import engine.entities.Entity;
import engine.scripts.defaults.GroovyScript;
import engine.util.Primitive;
import groovy.lang.GroovyClassLoader;

/**
 * Modifies qualities of Entity through Groovy
 * Acts as a wrapper class for GroovyScript
 * 
 * @author lasia
 * @author Albert
 */
public class Script implements IScript{
	private static final String FILEPATH = "src/engine/scripts/";
	private static final String SET = "set";
	private static final String GET = "get";
	private Class<IScript> myClazz;
	private Object myObject;
	private GroovyScript myScript;
	
//	TODO:Do something about all of those throws
    /**Creates a new Script from a GroovyClass
     * 
     * @param filename	name of GroovyClass file
     */
    public Script(String filename) throws InstantiationException, IllegalAccessException, CompilationFailedException, IOException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
    	GroovyClassLoader gcl = new GroovyClassLoader();
		myClazz = gcl.parseClass(new File (FILEPATH + filename));
		myObject = myClazz.newInstance();
		myScript = (GroovyScript) myObject;
    }
    
    /**Sets a field within the script to the desired input
     * 
     * @param field		field to be set
     * @param input		input to be set to
     */
    public void set(String field, Object input) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
    	Class<?> inputClass = input.getClass();
    	if(Primitive.isWrapper(inputClass)) {
    		inputClass = Primitive.getPrimitive(inputClass);
    	}
    	Method method = myClazz.getDeclaredMethod(SET + capitalize(field),inputClass);
    	method.invoke(myObject,input);
    }
    
    /**Gets a field within the script
     * 
     * @param field		field to get value from
     * @return value	value of field
     */
    public Object get(String field) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Method method = myClazz.getDeclaredMethod(GET + capitalize(field));
    	return method.invoke(myObject);
    }

	/**Capitalizes the first letter of a String - Should probably move to a different class
	 * 
	 * @param field		String to capitalize
	 * @return String	capitalized string
	 */
	private String capitalize(String field) {
		field = field.substring(0, 1).toUpperCase() + field.substring(1);
		return field;
	}
	
    @Override
    public void execute(Entity entity) {
    	myScript.execute(entity);
    }
    
	/**Gets the public fields from the script. 
	 * Keys are the names of the fields in the form of a string. 
	 * Values are the class types associated with that particular field.
	 * 
	 * @return Set of public fields. Fields returned can be used with get/set.
	 */
	public Map<String,Class<?>> getFields() {
		return myScript.getFields();
	}
    
}
