package authoring.panels;

import authoring.IPanelDelegate;
import authoring.Panel;
import authoring.PanelController;
import authoring.Screen;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class ObjectDetailsPanel implements Panel{

    @Override
    public Region getRegion() {
        return new Pane();
    }

    @Override
    public int getArea(){
        return Screen.TOP_RIGHT;
    }

    @Override
    public void setController(IPanelDelegate controller) {
        //TODO: Create controller
    }

    @Override
    public String title(){
        return "Object Details";
    }

}
