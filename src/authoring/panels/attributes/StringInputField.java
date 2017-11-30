package authoring.panels.attributes;

import java.lang.reflect.Method;

import util.exceptions.GroovyInstantiationException;

public class StringInputField extends InputField {
	

	public StringInputField(Object object, Method get, Method set) throws GroovyInstantiationException {
		super(object, get, set);
		// TODO Auto-generated constructor stub
	}
	
	public StringInputField(Object object, String field, Method get, Method set) throws GroovyInstantiationException {
		super(object, field, get, set);
	}

	@Override
	protected void updateField() {
	}

}
