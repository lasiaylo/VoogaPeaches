package authoring.panels.attributes;

/**Allows users to change a number field
 * @author lasia
 *
 */
public class NumberInputField extends InputField{

	public NumberInputField(Setter set) {
		super(set);
	}
	
	protected void getDefaultValue() {
		String defaultText = Double.toString((Double) getValue());
		getTextField().setText(defaultText);
	}

	@Override
	protected void updateField() {
		String text = getTextField().getText();
		if (!text.isEmpty()) {
			try {
			Number input = Double.parseDouble(text);
			setValue(input);
			} catch (NumberFormatException e) {
//				Don't allow to type letters, figure something out
			}
		}
	}
}
