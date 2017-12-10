package authoring.panels.attributes;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class TPane {

    public static Node addChildPane(String title, Node... pane) {
        VBox box = new VBox();
        box.getChildren().addAll(pane);
        return addPane(title, box);
    }

    public static Node addPane(String title, Node pane) {
        TitledPane tPane = new TitledPane(title, pane);
        tPane.setAnimated(false);
        return tPane;
    }
}
