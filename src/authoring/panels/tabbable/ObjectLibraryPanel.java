package authoring.panels.tabbable;

import authoring.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class ObjectLibraryPanel implements Panel{

    @Override
    public Region getRegion() {
        return new Pane();
    }

    @Override
    public ScreenPosition getPosition(){
        return ScreenPosition.TOP_LEFT;
    }

    @Override
    public void setController(IPanelDelegate controller) {
        //TODO: Create controller
    }

    @Override
    public String title(){
        return "Library";
    }

}
