package authoring.buttons;

import authoring.buttons.strategies.IButtonStrategy;
import javafx.scene.control.Button;

/**
 * A Custom Button
 * @author Albert
 */
public class CustomButton {

    private IButtonStrategy strategy;
    private Button button;

    /**
     * Creates a new Custom Button
     * @param strategy  IButtonStrategy strategy to use for the button
     * @param title     Title of button
     */
    public CustomButton(IButtonStrategy strategy, String title) {
        button = new Button(title);
        this.strategy = strategy;
        button.setOnAction(e -> strategy.fire());
    }

    /**
     * @return  javafx button created
     */
    public Button getButton() {
        return button;
    }
}