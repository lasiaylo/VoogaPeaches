package authoring.panels.attributes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import authoring.panels.attributes.Field;
import authoring.panels.attributes.NumberInputField;
import util.exceptions.GroovyInstantiationException;

public class FieldFactory {
	private static ResourceBundle myResources = ResourceBundle.getBundle("fields");
	private static final String GET = "get";
	private static final String SET = "set";
	
	public static Field makeField(Object attribute, String methodName) throws GroovyInstantiationException {
		try {
			methodName = capitalize(methodName);
			Class<?> clazz = attribute.getClass();
			Method getMethod = clazz.getDeclaredMethod(GET + methodName);
			
			Class<?> inputClass = getMethod.getReturnType();
			Method setMethod = clazz.getDeclaredMethod(SET + methodName, inputClass);
			
			System.out.println(inputClass);
			
			String fieldType = myResources.getString(inputClass.toString());
			Class<?> fieldClazz = Class.forName(fieldType);
			Constructor<?> ctor = fieldClazz.getConstructor(Object.class,Method.class,Method.class);
			Field field = (Field) ctor.newInstance(attribute,getMethod,setMethod);
			return field;
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new GroovyInstantiationException();
		}
	}
	
	/**Capitalizes the first letter of a String - Should probably move to a different class
	 *
	 * @param field		String to capitalize
	 * @return String	capitalized string
	 */
	private static String capitalize(String field) {
		field = field.substring(0, 1).toUpperCase() + field.substring(1);
		return field;
	}
}
