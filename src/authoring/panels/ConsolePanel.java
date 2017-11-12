package authoring.panels;

import authoring.Panel;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class ConsolePanel implements Panel{

    private TextArea consoleField = new TextArea();
    {
        consoleField.setEditable(false);
        consoleField.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));
    }

    @Override
    public Region getRegion() {
        return consoleField;
    }

    @Override
    public int getArea(){
        return authoring.Screen.BOTTOM_LEFT;
    }

    @Override
    public void setController() {

    }

    @Override
    public String title(){
        return "Console";
    }

}
