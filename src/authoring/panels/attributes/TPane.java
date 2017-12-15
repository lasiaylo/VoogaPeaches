package authoring.panels.attributes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class TPane {

    public static Node addChildPane(String title, Node... pane) {
        VBox box = createVBox(pane);
        return addPane(title, box);
    }

    private static VBox createVBox(Node[] pane) {
        VBox box = new VBox();
        box.getChildren().addAll(pane);
        return box;
    }

    public static Node addChildPane(Label title, Node ... pane){
        TitledPane tPane = new TitledPane();
        tPane.setGraphic(title);
        tPane.setContent(createVBox(pane));
        return tPane;
    }


    public static Node addPane(String title, Node pane) {
        TitledPane tPane = new TitledPane(title, pane);
        tPane.setAnimated(false);
        return tPane;
    }
}