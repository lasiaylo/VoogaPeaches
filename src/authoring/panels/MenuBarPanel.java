package authoring.panels;

import authoring.Panel;
import authoring.PanelController;
import authoring.Screen;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

/**
 * The Menu Bar is displayed at the top of the authoring environment and contains the buttons for options related to the environment. This includes saving and loading workspaces, as well as opening panels for viewing within the workspace.
 * @author Brian Nieves
 */
public class MenuBarPanel implements Panel {

    private HBox bar;
    private ResourceBundle properties = ResourceBundle.getBundle("screenlayout");
    private double height = Double.parseDouble(properties.getString("menubarheight"));
    private String style = properties.getString("menubarstyle");
    private Color textColor = Color.web(properties.getString("menubartextcolor"));
    private double spacing = Double.parseDouble(properties.getString("menubarspacing"));
    private Color color = Color.web(properties.getString("menubarcolor"));
    private Color onHoverColor = Color.web(properties.getString("menubaronhovercolor"));

    @Override
    public Region getRegion(){
        bar = new HBox();
        bar.setPrefHeight(height);
        bar.setStyle(style);

        Pane file = getOption("File"); //TODO: Set up a data file for these, set up action listeners to albert's menu
        Pane view = getOption("View");

        bar.getChildren().addAll(file, view);
        return bar;
    }

    /**
     * Creates an option to be put in the Menu Bar.
     * @param text the text for the option
     * @return a Pane that represents the field for the option
     */
    private Pane getOption(String text) {
        StackPane option = new StackPane();

        Text textbutton = new Text(text);
        textbutton.setFill(textColor);

        Rectangle box = new Rectangle();
        box.setWidth(textbutton.minWidth(height) + spacing);
        box.setHeight(height);
        box.setFill(color);

        option.setOnMouseEntered(event -> box.setFill(onHoverColor));
        option.setOnMouseExited(event -> box.setFill(color));

        option.getChildren().addAll(box, textbutton);
        return option;
    }

    @Override
    public int getArea(){
        return Screen.TOP;
    }

    @Override
    public void setController(PanelController controller) {
        //TODO: Create controller
    }

    @Override
    public String title(){
        return "Menu";
    }
}
