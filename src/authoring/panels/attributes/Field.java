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
	private Control myControl;
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
	public Field(Object object,Method get, Method set) throws GroovyInstantiationException {
		this(object, null, get, set);
	}

	/**Creates a Field that corresponds to a dynamic field
	 * @param object
	 * @param get
	 * @param set
	 * @param field
	 * @throws GroovyInstantiationException 
	 */
	public Field(Object object, String field, Method get, Method set) throws GroovyInstantiationException {
		myObject = object;
		myField = field;
		getMethod = get;
		setMethod = set;
		makeControl();
		getDefaultValue();
		setControlAction();
	}
	
	/**Creates the JavaFX Controller
	 * 
	 */
	protected abstract void makeControl();
	
	/**Sets the Controller action
	 * 
	 */
	protected abstract void setControlAction();

	/**Gets the value of the field and sets the Controller field to display it.
	 * @throws GroovyInstantiationException 
	 * 
	 */
	protected abstract void getDefaultValue() throws GroovyInstantiationException;

	/**
	 * @return Control
	 */
	public Control getControl() {
		return myControl;
	}
	
	/**Sets myControl
	 * @param control
	 */
	protected void setControl(Control control) {
		myControl = control;
	}
	
	/**Gets the desired field inside the object
	 * @return getMethod
	 * @throws GroovyInstantiationException 
	 */
	protected Object getValue() throws GroovyInstantiationException {
		try {
			if (myField == null) {
				return getMethod.invoke(myObject);
			}
			return getMethod.invoke(myObject, myField);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new GroovyInstantiationException();
		}
	}
	
	/**Sets the desired field inside the object
	 * @throws GroovyInstantiationException 
	 */
	protected void setValue(Object args) throws GroovyInstantiationException {
		try {
			if (myField == null)
				setMethod.invoke(myObject, args);
			else
				setMethod.invoke(myObject, myField, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new GroovyInstantiationException();
		}
	}
}
