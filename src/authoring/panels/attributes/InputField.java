package authoring.panels.attributes;

import javafx.scene.control.TextField;
import util.exceptions.GroovyInstantiationException;

/**A JavaFX TextField that changes an Entities field
 * @author lasia
 *
 */
public abstract class InputField extends Field{
	private TextField textfield;
	
	public InputField(Setter set) {
		super(set);
	}
	
	protected void makeControl() {
		textfield = new TextField();
		setControl(textfield);
	}
	
	protected void setControlAction() {
		textfield.setOnKeyTyped(e-> {
			try {
				updateField();
			} catch (GroovyInstantiationException e1) {
		
			}
		});
	}

	protected abstract void updateField() throws GroovyInstantiationException;

	protected TextField getTextField() {
		return textfield;
	}
}