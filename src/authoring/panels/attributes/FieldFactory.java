package authoring.panels.attributes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;

import authoring.panels.attributes.MethodSetter;
import util.exceptions.GroovyInstantiationException;

/**Creates a particular subclass of Field depending on the class of what the Field is setting
 * @author lasia
 *
 */
public class FieldFactory {
	private static ResourceBundle myResources = ResourceBundle.getBundle("fields");
	private static final String GET = "get";
	private static final String SET = "set";
	
	/**Creates a MethodField
	 * @param attribute
	 * @param methodName
	 * @return
	 * @throws GroovyInstantiationException
	 */
	public static Field makeField(Object attribute, String methodName) throws GroovyInstantiationException {
		try {
			Class<?> clazz = attribute.getClass();
			Method getMethod = clazz.getDeclaredMethod(GET + methodName);
			Class<?> inputClass = getMethod.getReturnType();
			Method setMethod = clazz.getDeclaredMethod(SET + methodName, inputClass);
			
			Setter set = new MethodSetter(attribute, getMethod, setMethod);
			return makeField(set, determineType(inputClass));
		
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) { 
			throw new GroovyInstantiationException();
		}
	}
	
	/**Creates a MapField
	 * @param map
	 * @param key
	 * @return
	 * @throws GroovyInstantiationException 
	 */
	public static Field makeField(Map<String, Object> map, String key) throws GroovyInstantiationException {
		Setter set = new MapSetter(map, key);
		Class<?> clazz = map.get(key).getClass();

		return makeField(set, determineType(clazz));
	}
	
	/**Creates a field
	 * @param set
	 * @param fieldType
	 * @return
	 * @throws GroovyInstantiationException
	 */
	public static Field makeField(Setter set,String fieldType) throws GroovyInstantiationException {
		Class<?> fieldClazz;
		try {
			fieldClazz = Class.forName(fieldType);
			Constructor<?> ctor = fieldClazz.getConstructor(Setter.class);
			Field field = (Field) ctor.newInstance(set);
			return field;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new GroovyInstantiationException();
		}
	}
	
	private static String determineType(Class<?> clazz) {
		return myResources.getString(clazz.toString());
	}
}
