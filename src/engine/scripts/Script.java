package engine.scripts;
import java.util.*;
import java.io.File;
import java.io.IOException;
import org.codehaus.groovy.control.CompilationFailedException;


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
	private IScript myScript;
	
    public Script(String filename) throws InstantiationException, IllegalAccessException, CompilationFailedException, IOException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	GroovyClassLoader gcl = new GroovyClassLoader();
		myClazz = gcl.parseClass(new File (FILEPATH + filename));
		myObject = myClazz.newInstance();
		myScript = (IScript) myObject;
//		printMethods(myClazz);
		Object[] input = new Integer[] {2};
		set("dude",input);
		get("dude");
		System.out.println(getFields());
    }
    
    public void set(String field, Object[] input) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Method method = myClazz.getDeclaredMethod(SET + capitalize(field));
    	System.out.println(method.invoke(myObject));
    }
    
    public void get(String field) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Method method = myClazz.getDeclaredMethod(GET + capitalize(field));
    	System.out.println(method.invoke(myObject));
    	
    }

	private String capitalize(String field) {
		field = field.substring(0, 1).toUpperCase() + field.substring(1);
		return field;
	}
	private void printMethods(Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		Method[] methods = myClazz.getDeclaredMethods();
		Object groovyScript = myClazz.newInstance();
		
		Method method = myClazz.getDeclaredMethod("getDude",null);
		System.out.println(method.invoke(myObject, null));
//		for (int i = 0; i < methods.length; i++) {
//			String a = methods[i].toString();
//			if (a.contains("setDude")) {
//				System.out.println(a);
//				methods[i].invoke(groovyScript, 5);
//			}
//			if (a.contains("getDude")) {
//				System.out.println(a);
//				System.out.println(methods[i].invoke(groovyScript,null));
//			}
//		}
	}
	
    @Override
    public void execute(Entity entity) {
    	myScript.execute(entity);
    }

	@Override
	public Set<?> getFields() {
		return myScript.getFields();
	}
    
}
