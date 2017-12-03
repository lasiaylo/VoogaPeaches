package authoring.panels.tabbable;

import authoring.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

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
//        System.out.println("add");
//        myController = controller;
//        myPane.getChildren().add(myController.getMiniMap());

    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
