package authoring.panels.tabbable;

import authoring.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class MiniMapPanel implements Panel{

    @Override
    public Region getRegion() {
        return new Pane();
    }

    @Override
    public void setController(IPanelDelegate controller) {
    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
