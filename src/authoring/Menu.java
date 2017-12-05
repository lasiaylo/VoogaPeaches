package authoring;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import util.PropertiesReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * This is the specific window for the Menu with the different buttons for each simulation, subclass of Window
 * All user interactions are determined and executed in here
 *
 * @author Kelly Zhang
 * @author Dara Buggay
 *
 */
public class Menu {

    private static final String AUTHORING_ENVIRONMENT = "AUTHORING";
    private static final String PLAYER = "PLAY";
    private static final String SETTINGS = "SETTINGS";
    private static final String AUTHORINGPIC = "data/menudata/authoring.png";
    private static final String PLAYERPIC = "data/menudata/player.png";
    private static final String SETTINGSPIC = "data/menudata/settings.png";

    private static final double WIDTH = Double.parseDouble(PropertiesReader.value("menulayout", "width"));
    private static final double HEIGHT = Double.parseDouble(PropertiesReader.value("menulayout", "height"));

    private static final int BUTTONOFFSET = 50;
    private static final int BUTTONS_PER_LINE = 3;
    private List<Button> buttons;

    private Scene myScene;
    private Group myRoot;

    public Menu() {
        myRoot = new Group();
        setupSceneDimensions();
        setupScene();
    }

    private void setupSceneDimensions() {
        myScene = new Scene(myRoot, WIDTH, HEIGHT);
    }

    public void setupScene() {
        addButtons();
        //addTitle();
    }

    private void chooseSim() { //http://www.java2s.com/Code/Java/JavaFX/AddClickactionlistenertoButton.htm
        for (int i = 0; i < buttons.size(); i ++) {
            Button button = buttons.get(i);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    System.out.println("clicked");
//                    simChoice = getSimFromFile(button);
//                    System.out.println("button pressed!!");
//                    //System.out.println(file);
                }
            });

        }
    }

    private void resetMenu() {
//        simChoice = null;
    }

//    private Stage getStageFromFile(Button buttonPressed) {
//        String simFileString = buttonPressed.getAccessibleText();
//        simFileString += ".xml";
//        ClassLoader cl = getClass().getClassLoader();
//        File simFile = new File(cl.getResource(simFileString).getFile());
//        XMLParser parser = new XMLParser();
//        parser.buttonChooseFile(simFile);
//        return parser.getSimulation();
//    }

    private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
        //http://docs.oracle.com/javafx/2/ui_controls/button.htm
        Button authoringButton = createMenuButton(AUTHORINGPIC, AUTHORING_ENVIRONMENT);
        Button playerButton = createMenuButton(PLAYERPIC, PLAYER);
        Button settingsButton = createMenuButton(SETTINGSPIC, SETTINGS);

        buttons = new ArrayList<Button>(Arrays.asList(authoringButton, playerButton, settingsButton));
        double buttonXPadding = (WIDTH - BUTTONOFFSET*2 - buttons.get(0).getWidth())/BUTTONS_PER_LINE;
        double buttonYPadding = (HEIGHT/2 - BUTTONOFFSET - buttons.get(0).getHeight()*2)/BUTTONS_PER_LINE;

        int line = 0;
        while (line <= Math.floor(buttons.size()/BUTTONS_PER_LINE)) {
            for (int i = line; i < line + BUTTONS_PER_LINE; i++) {
                if (line*BUTTONS_PER_LINE + i < buttons.size()) {
                    Button button = buttons.get(line*BUTTONS_PER_LINE + i);
                    setMenuButtonLayout(button, BUTTONOFFSET + button.getMaxWidth() + i*buttonXPadding, HEIGHT/2 + line*buttonYPadding);
                }
            }
            line++;
        }
        myRoot.getChildren().addAll(buttons);
    }

    private void setMenuButtonLayout(Button button, Double x, Double y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    private Button createMenuButton(String imageName, String buttonText) {
        Image buttonImage = new Image(imageName);
        Button myButton = new Button();
        myButton.setTooltip(new Tooltip(buttonText));

//        myButton.setScaleX(0.75);
//        myButton.setScaleY(0.75);

        myButton.setGraphic(new ImageView(buttonImage));
        myButton.setAccessibleText(buttonText);

        return myButton;
    }

//    private void addTitle() {
//        Image titleImage = new Image(getClass().getClassLoader().getResourceAsStream("cellsociety.png"));
//        ImageView title = new ImageView(titleImage);
//        title.setLayoutX(WIDTH / 2 - title.getBoundsInLocal().getWidth() / 2);
//        title.setLayoutY(HEIGHT * 1 / 3 - title.getBoundsInLocal().getHeight() / 2);
//        myRoot.getChildren().add(title);
//    }

    public Scene getScene() {
        return myScene;
    }
}

