package authoring.panels.attributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;

/**A checkbox that gets/sets an object's field to what is checked
 * @author lasia
 *
 */
public class BooleanField extends Field{
	private CheckBox checkbox;
	private boolean defaultValue;
	
	public BooleanField(Object object, Method get, Method set) {
		super(object, get, set);
		checkbox = new CheckBox();
		configController();
	}
	
	private void configController() {
		try {
			defaultValue = (boolean) getValue();
			checkbox.setSelected(defaultValue);
			checkbox.setOnAction(e -> updateField());
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace(); //SOMEBODY HELP ME WITH EXCEPTIONS ;]
		};
	}

	private void updateField() {
		boolean checked = checkbox.isSelected();
		try {
			setMethod().invoke(getObject(), checked);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Control getControl() {
		return checkbox;
	}

}
