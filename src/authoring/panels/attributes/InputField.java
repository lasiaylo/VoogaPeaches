package authoring.panels.attributes;

import java.lang.reflect.Method;
import javafx.scene.control.TextField;
import util.exceptions.GroovyInstantiationException;

/**A JavaFX TextField that changes an Entities field
 * @author lasia
 *
 */
public abstract class InputField extends Field{
	private TextField textfield;
	
	public InputField(Object object, Method get, Method set) throws GroovyInstantiationException {
		super(object, get, set);
	}
	
	public InputField(Object object, String field, Method get, Method set) throws GroovyInstantiationException {
		super(object, field, get, set);
	}
	
	protected void makeControl() {
		textfield = new TextField();
		setControl(textfield);
	}
	
	protected void setControlAction() {
		textfield.setOnKeyTyped(e-> {
			try {
				updateField();
			} catch (GroovyInstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	protected abstract void updateField() throws GroovyInstantiationException;

	protected TextField getTextField() {
		return textfield;
	}

}
