package authoring;

import engine.EntityManager;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 *
 * Used to act as controller for the panels. Instead of passing around a specific instance of a class, an instance of
 * this interface is used with the below methods that delegate the tasks however the specific class wants to. The Panels
 * rely on an instance of this delegate to handle the below methods
 *
 * @author Simran
 */
public interface IPanelController {

    ScrollPane getCamera();

    EntityManager getManager();

    void play();

    void pause();

    void save(String name);

    Pane getMiniMap();
}
