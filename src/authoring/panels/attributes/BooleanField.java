package authoring.panels.attributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;

/**A checkbox that gets/sets an object's field to what is checked
 * @author lasia
 *
 */
public class BooleanField extends InputField{
	private CheckBox checkbox;
	private boolean defaultValue;
	
	public BooleanField(Object object, Method get, Method set) {
		super(object, get, set);
		checkbox = new CheckBox();
		configController();
	}
	
	
	
	private void configController() {
		try {
			defaultValue = (boolean) getMethod().invoke(getObject());
			checkbox.setSelected(defaultValue);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		};
		
	}



	@Override
	public Control getControl() {
		return checkbox;
	}

}
