package authoring.panels.attributes;

/**Allows users to change a String Field
 * @author lasia
 *
 */
public class StringInputField extends InputField {
	
	public StringInputField(Setter set) {
		super(set);
	}

	@Override
	protected void updateField() {
		String text = getTextField().textProperty().getValue();
		setValue(text);
	}

	protected void getDefaultValue() {
		String defaultText = (String) getValue();
		getTextField().setText(defaultText);
	}
}