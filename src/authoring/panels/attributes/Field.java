package authoring.panels.attributes;

import javafx.scene.Node;
import util.exceptions.GroovyInstantiationException;

/**A JavaFX Node that changes a particular field 
 * @author lasia
 *
 */
public abstract class Field {
	private Node myControl;
	private Setter mySetter;
	
	/**Creates a new Field that needs a way of setting
	 * @param setter
	 */
	public Field(Setter setter) {
		mySetter = setter;
		makeControl();
		setControlAction();
		getDefaultValue();
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
	protected abstract void getDefaultValue();

	/**
	 * @return Control
	 */
	public Node getControl() {
		return myControl;
	}
	
	/**Sets myControl
	 * @param control
	 */
	protected void setControl(Node control) {
		myControl = control;
	}
	
	protected Object getValue() {
		return mySetter.getValue();
	}
	
	protected void setValue(Object arg) {
		mySetter.setValue(arg);
	}
	
}
