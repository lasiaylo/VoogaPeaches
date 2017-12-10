package authoring.panels.attributes;

import java.text.DecimalFormat;

/**Allows users to change a number field
 * @author lasia
 *
 */
public class NumberInputField extends InputField{

	public NumberInputField(Setter set) {
		super(set);
	}
	
	protected void getDefaultValue() {
		Number number = (Number) getValue();
		DecimalFormat format = new DecimalFormat("#.######");
		String defaultText = format.format(number);
		getTextField().setText(defaultText);
	}

	@Override
	protected void updateField() {
		String text = getTextField().textProperty().getValue();
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
