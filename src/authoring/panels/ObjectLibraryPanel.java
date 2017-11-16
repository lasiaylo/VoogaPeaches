package authoring.panels;

import authoring.Panel;
import authoring.PanelController;
import authoring.Screen;
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
    public int getArea(){
        return Screen.TOP_LEFT;
    }

    @Override
    public void setController(PanelController controller) {
        //TODO: Create controller
    }

    @Override
    public String title(){
        return "Library";
    }

}
