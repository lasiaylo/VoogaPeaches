package authoring.panels.attributes;

import javafx.scene.control.CheckBox;
import util.exceptions.GroovyInstantiationException;

/**A checkbox that gets/sets an object's field to what is checked
 * @author lasia
 *
 */
public class BooleanField extends Field{
	private CheckBox checkbox;
	private boolean defaultValue;
	
	public BooleanField(Setter setter) throws GroovyInstantiationException {
		super(setter);
	}
	
	@Override
	protected void makeControl() {
		setControl(new CheckBox());
	}

	@Override
	protected void setControlAction() {
		checkbox.setOnAction(e ->updateField());
	}

	@Override
	protected void getDefaultValue() {
		defaultValue = (boolean) getValue();
		checkbox.setSelected(defaultValue);
	}
	
	protected void updateField() {
		boolean checked = checkbox.isSelected();
		setValue(checked);
	}


}
