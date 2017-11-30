package authoring.panels.attributes;

import java.lang.reflect.Method;

import util.exceptions.GroovyInstantiationException;

/**Allows users to change a String Field
 * @author lasia
 *
 */
public class StringInputField extends InputField {
	
	public StringInputField(Object object, Method get, Method set) throws GroovyInstantiationException {
		super(object, get, set);
	}
	
	public StringInputField(Object object, String field, Method get, Method set) throws GroovyInstantiationException {
		super(object, field, get, set);
	}

	@Override
	protected void updateField() {
	}

	protected void getDefaultValue() throws GroovyInstantiationException {
		String defaultText = (String) getValue();
		getTextField().setText(defaultText);
	}


}
