package authoring.panels.attributes;

import util.exceptions.GroovyInstantiationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**A Setter that sets an object's field to what is inputted from the user
 * @author lasia
 *
 */
public class MethodSetter implements Setter{

	private Object myObject;
	private String myField;
	private Method getMethod;
	private Method setMethod;

	/**Creates a Field that corresponds to a static field
	 * @param object
	 * @param get
	 * @param set
	 * @throws GroovyInstantiationException 
	 */
	public MethodSetter(Object object,Method get, Method set) throws GroovyInstantiationException {
		myObject = object;
		getMethod = get;
		setMethod = set;
	}

	public Object getValue() {
		try {
			if (myField == null) {
				return getMethod.invoke(myObject);
			}
			return getMethod.invoke(myObject, myField);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { }
		return null;
	}

	public void setValue(Object arg) {
		try {
			if (myField == null)
				setMethod.invoke(myObject, arg);
			else
				setMethod.invoke(myObject, myField, arg);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { }
	}
}