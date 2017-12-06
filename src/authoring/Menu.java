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
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * This is the specific window for the Menu with the different buttons for each simulation, subclass of Window
 * All user interactions are determined and executed in here
 *
 * @author Kelly Zhang
 *
 */
public class Menu {
    
    private static final String AUTHORING_ENVIRONMENT = "AUTHORING";
    private static final String PLAYER = "PLAY";
    private static final String SETTINGS = "SETTINGS";
    private static final String AUTHORINGPIC = "resources/menuImages/authoring.png";
    private static final String PLAYERPIC = "resources/menuImages/player.png";
    private static final String SETTINGSPIC = "resources/menuImages/settings.png";

    private static final double WIDTH = Double.parseDouble(PropertiesReader.value("menulayout", "width"));
    private static final double HEIGHT = Double.parseDouble(PropertiesReader.value("menulayout", "height"));

    private List<Button> buttons;

    private Stage myStage;
    private Scene myScene;
    private Group myRoot;

    public Menu() {
        myStage = new Stage();
        myRoot = new Group();

        setupSceneDimensions();
        setupScene();

        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.show();

        formatButtons();
        updateTheme();
    }

    private void updateTheme() {
        PubSub.getInstance().subscribe(
                PubSub.Channel.THEME_MESSAGE,
                (message) -> {
                    if (myRoot.getStylesheets().size() >= 1) {
                        myRoot.getStylesheets().remove(0);
                    }
                    myRoot.getStylesheets().add(((ThemeMessage) message).readMessage());
                }
        );
    }

    private void setupSceneDimensions() {
        myScene = new Scene(myRoot, WIDTH, HEIGHT);
    }

    public void setupScene() {
        addButtons();
        addTitle();
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

//    private void resetMenu() {
////        simChoice = null;
//    }
//
//    private Stage onButtonPressed(Button buttonPressed) {
//        String simFileString = buttonPressed.getAccessibleText();
//        simFileString += ".xml";
//        ClassLoader cl = getClass().getClassLoader();
//        File simFile = new File(cl.getResource(simFileString).getFile());
//        return null;
//    }

    private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
        //http://docs.oracle.com/javafx/2/ui_controls/button.htm
        Button authoringButton = createMenuButton(AUTHORINGPIC, AUTHORING_ENVIRONMENT);
        Button playerButton = createMenuButton(PLAYERPIC, PLAYER);
        Button settingsButton = createMenuButton(SETTINGSPIC, SETTINGS);

        buttons = new ArrayList<Button>(Arrays.asList(authoringButton, playerButton, settingsButton));
        myRoot.getChildren().addAll(buttons);
    }

    private void formatButtons() {
        int numButtons = buttons.size();

        double buttonXOffset = WIDTH/(numButtons+1);
        double buttonYOffset = HEIGHT*2/3;

        for (int i = 0; i < numButtons; i++) {
            setMenuButtonLayout(buttons.get(i), buttonXOffset*(i+1) - buttons.get(i).getWidth()/2, buttonYOffset);
        }
    }

    private void setMenuButtonLayout(Button button, double x, double y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    private Button createMenuButton(String imageName, String buttonText) {
        File buttonImage = new File(imageName);
        Button myButton = new Button();
        myButton.setGraphic(new ImageView(buttonImage.toURI().toString()));

        myButton.setTooltip(new Tooltip(buttonText));
        myButton.setAccessibleText(buttonText);

        return myButton;
    }

    private void addTitle() {
        File myImage = new File("resources/menuImages/authoring.png");
        ImageView title = new ImageView(myImage.toURI().toString());
        title.setLayoutX(WIDTH / 2 - title.getBoundsInLocal().getWidth() / 2);
        title.setLayoutY(HEIGHT * 1 / 3 - title.getBoundsInLocal().getHeight() / 2);
        myRoot.getChildren().add(title);
    }

    public Scene getScene() {
        return myScene;
    }
}

