package authoring.panels.attributes;

import java.lang.reflect.Method;
import javafx.scene.control.Control;

/**An Field that sets an object's field to what is inputted from the user
 * @author lasia
 *
 */
public abstract class Field {
	private Object myObject;
	private Method getMethod;
	private Method setMethod;
	
	
	/**Creates a Field
	 * @param object
	 * @param get
	 * @param set
	 */
	public Field(Object object,Method get, Method set) {
		myObject = object;
		getMethod = get;
		setMethod = set;
}
	/**
	 * @return Control
	 */
	public abstract Control getControl();
	
	/**
	 * @return myObject
	 */
	protected Object getObject() {
		return myObject;
	}
	/**
	 * @return getMethod
	 */
	protected Method getMethod() {
		return getMethod;
	}
	
	/**
	 * @return setMethod
	 */
	protected Method setMethod() {
		return setMethod;
	}
}
