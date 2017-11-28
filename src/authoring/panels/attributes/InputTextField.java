package authoring.panels.attributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class InputTextField extends InputField{
	private TextField textfield;
	
	public InputTextField(Object object, Method get, Method set) {
		super(object, get, set);
		textfield = new TextField();
		configController();
	}
	
	private void configController() {
			try {
				String defaultText = (String) getMethod().invoke(getObject());
				textfield.setText(defaultText);
				textfield.setOnAction(e-> updateField());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
	}
	
	private void updateField() {
		String input = textfield.getText();
		
	}

	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
