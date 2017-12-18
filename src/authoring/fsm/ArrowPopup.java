package authoring.fsm;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import util.PropertiesReader;

/**
 * This class is responsible for the popup on clicks of the arrow. The main purpose was to help clean up the arrow class
 * and keep it separate from the popup.
 *
 * @author Simran
 */
class ArrowPopup {

    private GraphDelegate myGraph;
    private Stage myStage = new Stage();
    private Arrow myArrow;

    /**
     * @param graph GraphDelegate for the Arrow
     * @param arrow The Arrow this is a helper Popup for
     */
    ArrowPopup(GraphDelegate graph, Arrow arrow) {
        myGraph = graph;
        myArrow = arrow;
        Scene scene = new Scene(new Group());
        FlowPane flow = createPopup();
        scene.setRoot(flow);
        myStage.setScene(scene);
    }

    Stage getStage() { return myStage; }

    /**
     * @return Content of the popup on click of the arrow
     */
    private FlowPane createPopup() {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button delete = new Button(PropertiesReader.value("fsm", "DELETE"));
        Button save = new Button(PropertiesReader.value("fsm", "SAVE"));
        TextField name = new TextField();
        name.setPromptText(PropertiesReader.value("fsm", "ENTER_YOUR_CLOSURE"));
        name.setText(myArrow.getMyCode());
        delete.setOnMouseClicked(e -> onDelete(delete));
        save.setOnMouseClicked(e -> onSave(save, name.getText()));
        flow.getChildren().addAll(delete, save, name);
        return flow;
    }

    /**
     * Calls on the graph delegate to remove itself from the list of all arrows
     *
     * @param delete Delete button needed to delete the new popup
     */
    private void onDelete(Button delete) {
        ((Stage) delete.getScene().getWindow()).close();
        myGraph.removeMyself(myArrow);
    }

    /**
     * @param save Delete button needed to delete the new popup
     * @param code The code input into the arrow
     */
    private void onSave(Button save, String code) {
        myArrow.setMyCode(code);
        ((Stage) save.getScene().getWindow()).close();
    }

}
