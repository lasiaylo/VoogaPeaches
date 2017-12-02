package authoring.panels.tabbable;

import authoring.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class MiniMapPanel implements Panel{

    private Pane myPane;

    public MiniMapPanel() {
        myPane = new Pane();
        myPane.getStyleClass().add("panel");
    }

    @Override
    public Region getRegion() {
        return myPane;
    }

    @Override
    public void setController(IPanelController controller) {
        //TODO: Create controller
    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
