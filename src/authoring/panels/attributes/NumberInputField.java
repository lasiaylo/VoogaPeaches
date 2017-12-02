package authoring.panels.attributes;

import java.lang.reflect.Method;

import util.exceptions.GroovyInstantiationException;

/**Allows users to change a number field
 * @author lasia
 *
 */
public class NumberInputField extends InputField{

	public NumberInputField(Object object, Method get, Method set) throws GroovyInstantiationException {
		super(object, get, set);
	}
	
	public NumberInputField(Object object, String field, Method get, Method set) throws GroovyInstantiationException {
		super(object, field, get, set);
	}
	
	protected void getDefaultValue() throws GroovyInstantiationException {
		String defaultText = String.valueOf(getValue());
		getTextField().setText(defaultText);
	}

	@Override
	protected void updateField() throws GroovyInstantiationException {
		String text = getTextField().getText();
//		if (!text.isEmpty()) {
			try {
			Number input = Double.parseDouble(text);
			setValue(input);
			} catch (NumberFormatException e) {
//				Don't allow to type letters, figure something out
//			}
		}
	}

}
