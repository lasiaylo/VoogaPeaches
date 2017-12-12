package authoring.panels.attributes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;

import util.ErrorDisplay;
import util.PropertiesReader;
import util.exceptions.GroovyInstantiationException;

/**Creates a particular subclass of Field depending on the class of what the Field is setting
 * @author lasia
 *
 */
public class FieldFactory {

    private static final String FIELD = "fields";
	private static final String GET = "get";
	private static final String SET = "set";
    public static final String REGEX_MATCHING_STRING = "^.+(\\.)(gif|GIF|png|PNG|jpg|JPG)+";
    public static final String IMAGE = "Image";

    public static Field makeField(Object attribute) throws GroovyInstantiationException {
		Setter set = new ObjectSetter(attribute);
		return makeField(set, determineType(attribute));
	}
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
			return makeField(set, determineType(attribute));
		
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
	public static Field makeFieldMap(Map<String, Object> map, String key) throws GroovyInstantiationException {
		Setter set = new MapSetter(map, key);
		Object object  = map.get(key);
		System.out.println("\n\n" + key + "  " + object.getClass() + "\n\n");

		return makeField(set, determineType(object));
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

			throw new GroovyInstantiationException();
		}
	}
	
	private static String determineType(Object obj) {
		try {
			Class<?> clazz = obj.getClass();
			if (clazz.equals(String.class)) {
				String string = (String) obj;
				if (string.matches(REGEX_MATCHING_STRING)) {
					return PropertiesReader.value(FIELD, IMAGE);
				}
			}
			return PropertiesReader.value(FIELD, clazz.toString());
		} catch (Exception e) {
			e.printStackTrace();
			new ErrorDisplay("Field Factory Error", "Could not read properties").displayError();
		}
		return null;
	}
}