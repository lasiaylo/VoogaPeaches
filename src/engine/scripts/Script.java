package engine.scripts;
import java.util.*;
import java.io.File;
import java.io.IOException;
import org.codehaus.groovy.control.CompilationFailedException;

import backend.util.Primitive;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import engine.entities.Entity;
import groovy.lang.GroovyClassLoader;

/**Modifies qualities of Entity through Groovy
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
	
    /**
     * @param filename
     */
    public Script(String filename) throws InstantiationException, IllegalAccessException, CompilationFailedException, IOException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
    	GroovyClassLoader gcl = new GroovyClassLoader();
		myClazz = gcl.parseClass(new File (FILEPATH + filename));
		myObject = myClazz.newInstance();
		myScript = (GroovyScript) myObject;
    }
    
    public void set(String field, Object input) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
    	Class<?> inputClass = input.getClass();
    	if(Primitive.isWrapper(inputClass)) {
    		inputClass = Primitive.getPrimitive(inputClass);
    	}
    	Method method = myClazz.getDeclaredMethod(SET + capitalize(field),inputClass);
    	method.invoke(myObject,input);
    }
    
    public Object get(String field) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Method method = myClazz.getDeclaredMethod(GET + capitalize(field));
    	return method.invoke(myObject);
    }

	private String capitalize(String field) {
		field = field.substring(0, 1).toUpperCase() + field.substring(1);
		return field;
	}
	
    @Override
    public void execute(Entity entity) {
    	myScript.execute(entity);
    }

	public Set<?> getFields() {
		return myScript.getFields();
	}
    
}
