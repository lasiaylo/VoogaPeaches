package authoring.menu;

import authoring.PanelController;
import authoring.panels.attributes.Field;
import authoring.panels.attributes.FieldFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.exceptions.GroovyInstantiationException;

public class GameSavePrompt {
    private String name;
    private Stage stage;
    private Field field;
    private Button saveButton;
    private Button closeButton;
    private PanelController panelController;
    private VBox myVBox;


    public GameSavePrompt(PanelController panelController) {
        name = "Name to be saved...";
        stage =  new Stage();
        this.panelController = panelController;
        myVBox = new VBox();
        makeField();
        setupStage(myVBox);
        makeButtons();
        stage.show();
    }

    private void makeButtons() {
        HBox hbox = new HBox();
        closeButton = new Button("Close");
        closeButton.setOnAction(e->close());
        System.out.println(stage.getWidth());
        saveButton = new Button("Save");
        saveButton.setOnAction(e->save());
        hbox.getChildren().addAll(closeButton,saveButton);
        myVBox.getChildren().add(hbox);
    }

    private void close() {
        stage.close();
    }

    private void makeField() {
        Node node = null;
        try {
            field = FieldFactory.makeField(name);
            node = field.getControl();
            node.setOnKeyPressed(e->handle(e));
        } catch (GroovyInstantiationException e) { }
        myVBox.getChildren().add(node);
    }

    private void setupStage(Parent parent) {
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

    }

    private void handle(KeyEvent e) {
        KeyCode key = e.getCode();
        if (key == KeyCode.ENTER){
            save();
        }
    }

    private void save() {
        name = (String) field.getValue();
        panelController.save(name);
        close();
    }

    public String getName(){
        return name;
    }
}
