package authoring.panels.attributes;

import java.lang.reflect.Method;
import javafx.scene.control.TextField;
import util.exceptions.GroovyInstantiationException;

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
			textfield.setOnAction(e-> updateField());
	}

	protected void getDefaultValue() throws GroovyInstantiationException {
		String defaultText = (String) getValue();
		textfield.setText(defaultText);
	}
	
	protected abstract void updateField();

	protected TextField getTextField() {
		return textfield;
	}
	


}
