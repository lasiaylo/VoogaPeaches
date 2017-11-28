package authoring.panels.attributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public abstract class InputField extends Field{
	private TextField textfield;
	
	public InputField(Object object, Method get, Method set) {
		super(object, get, set);
		textfield = new TextField();
		configController();
	}
	
	private void configController() {
			try {
				setDefault();
				textfield.setOnAction(e-> updateField());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
	}

	private void setDefault() throws IllegalAccessException, InvocationTargetException {
		String defaultText = (String) getMethod().invoke(getObject());
		textfield.setText(defaultText);
	}
	
	protected abstract void updateField();

	@Override
	public Control getControl() {
		return textfield;
	}
	

}
