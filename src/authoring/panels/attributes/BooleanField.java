package authoring.panels.attributes;

import java.lang.reflect.Method;
import javafx.scene.control.CheckBox;
import util.exceptions.GroovyInstantiationException;

/**A checkbox that gets/sets an object's field to what is checked
 * @author lasia
 *
 */
public class BooleanField extends Field{
	private CheckBox checkbox;
	private boolean defaultValue;
	
	public BooleanField(Object object, String field, Method get, Method set) throws GroovyInstantiationException {
		super(object, field, get, set);
	}
	
	public BooleanField(Object object, Method get, Method set) throws GroovyInstantiationException {
		super(object, get, set);
	}

	@Override
	protected void makeControl() {
		setControl(new CheckBox());
	}

	@Override
	protected void setControlAction() {
		checkbox.setOnAction(e -> {
			try {
				updateField();
			} catch (GroovyInstantiationException e1) {
				e1.printStackTrace();
			}
		});
	}

	@Override
	protected void getDefaultValue() throws GroovyInstantiationException {
		defaultValue = (boolean) getValue();
		checkbox.setSelected(defaultValue);
	}
	
	protected void updateField() throws GroovyInstantiationException {
		boolean checked = checkbox.isSelected();
		setValue(checked);
	}


}
