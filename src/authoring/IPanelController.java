package authoring;


import authoring.panels.reserved.CameraPanel;
import javafx.scene.control.ScrollPane;
import util.math.num.Vector;

import java.awt.*;

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

    void addBGTile(Vector pos);

    void selectLayer(int layer);
}
