package authoring.panels.tabbable;

import authoring.*;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MiniMapPanel implements Panel{

    private Pane myPane;
    private IPanelController myController;

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
        myController = controller;
        myPane = myController.getMiniMap();
        myPane.setPadding(new Insets(20));

    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
