package authoring.panels.attributes;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import util.exceptions.GroovyInstantiationException;
import util.math.num.Vector;

public class VectorField extends Field {

	private static final String REGEX_COMMA = ",";
	private TextField myX;
	private TextField myY;
	
	public VectorField(Setter set) {
		super(set);
	}

	@Override
	protected void makeControl() {
		myX = new TextField();
		myY = new TextField();
		setControl(new HBox(myX, myY));
	}

	@Override
	protected void setControlAction() {
		setOnAction(myX);
		setOnAction(myY);
	}
	
	private void setOnAction(TextField field) {
		field.setOnAction(e->{
			try {
				updateField();
			} catch (GroovyInstantiationException e1) {
				// do nothing
			}
		});
	}
	
	@Override
	protected void getDefaultValue() {
		String vector = getValue().toString();
		vector = vector.substring(1, vector.length() - 1);
		String[] scalars = vector.split(REGEX_COMMA);
		myX.setText(scalars[0]);
		myY.setText(scalars[1]);
	}
	
	private void updateField() throws GroovyInstantiationException {
		double xVal = Double.parseDouble(myX.getText());
		double yVal = Double.parseDouble(myY.getText());
		Vector newVect = new Vector(xVal,yVal);
		setValue(newVect);
	}
}