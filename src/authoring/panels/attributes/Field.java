package authoring.panels.attributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.scene.control.Control;
import util.exceptions.GroovyInstantiationException;

/**An Field that sets an object's field to what is inputted from the user
 * @author lasia
 *
 */
public abstract class Field {
	private Object myObject;
	private String myField;
	private Method getMethod;
	private Method setMethod;
	
	
	/**Creates a Field that corresponds to a static field
	 * @param object
	 * @param get
	 * @param set
	 */
	public Field(Object object,Method get, Method set) {
		myObject = object;
		getMethod = get;
		setMethod = set;
	}
	
	/**Creates a Field that corresponds to a dynamic field
	 * @param object
	 * @param get
	 * @param set
	 * @param field
	 */
	public Field(Object object, Method get, Method set, String field) {
		this(object, get, set);
		myField = field;
	}
	
	/**
	 * @return Control
	 */
	public abstract Control getControl();
	
	/**
	 * @return getMethod
	 * @throws GroovyInstantiationException 
	 */
	protected Object getValue() throws GroovyInstantiationException {
		try {
			if (myField.equals(null))
				return getMethod.invoke(myObject);
			return getMethod.invoke(myObject, myField);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new GroovyInstantiationException();
		}
	}
	
	/**
	 * @return setMethod
	 * @throws GroovyInstantiationException 
	 */
	protected Object setValue(Object args) throws GroovyInstantiationException {
		try {
			if (myField.equals(null))
				return setMethod.invoke(myObject, args);
			
			return setMethod.invoke(myObject, myField, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new GroovyInstantiationException();
		}
	}
}
