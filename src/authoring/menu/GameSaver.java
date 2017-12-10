package authoring.menu;

import authoring.panels.attributes.Field;
import authoring.panels.attributes.FieldFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.exceptions.GroovyInstantiationException;

public class GameSaver {
    private String name;
    private Stage stage;
    private Field field;

    public GameSaver() {
        name = "Name to be saved...";
        stage =  new Stage();
        try {
            Field field = FieldFactory.makeField(name);
            Node node = field.getControl();
            node.setOnKeyPressed(e->handle(e));
            setupStage(node);
        } catch (GroovyInstantiationException e) { }


    }

    private void setupStage(Node node) {
        Scene scene = new Scene((Parent) node);
        stage.setScene(scene);
        stage.show();

    }

    private void handle(KeyEvent e) {
        KeyCode key = e.getCode();
        if (key == KeyCode.ENTER){
            name = (String) field.getValue();
            System.out.println(name);
            stage.close();
        }
    }

    public String getName(){
        return name;
    }
}
