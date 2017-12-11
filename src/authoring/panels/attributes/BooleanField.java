package authoring.panels.attributes;

import javafx.scene.control.CheckBox;
import util.exceptions.GroovyInstantiationException;

/**A checkbox that gets/sets an object's field to what is checked
 * @author lasia
 *
 */
public class BooleanField extends Field{

	private CheckBox checkbox;
	
	public BooleanField(Setter setter) throws GroovyInstantiationException {
		super(setter);
	}
	
	@Override
	protected void makeControl() {
		checkbox = new CheckBox();
		setControl(checkbox);
	}

	@Override
	protected void setControlAction() {
		checkbox.setOnAction(e ->updateField());
	}

	@Override
	protected void getDefaultValue() {
		boolean defaultValue = (boolean) getValue();
		checkbox.setSelected(defaultValue);
	}
	
	protected void updateField() {
		boolean checked = checkbox.isSelected();
		setValue(checked);
	}
}