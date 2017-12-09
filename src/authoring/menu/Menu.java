package authoring.menu;

import authoring.Screen;
import database.User;
import database.firebase.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.VoogaPeaches;
import util.PropertiesReader;
import util.exceptions.ObjectIdNotFoundException;
import util.pubsub.PubSub;
import util.pubsub.messages.StringMessage;

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
    private Pane myRoot;
    private Screen authoring;
    private String currentTheme;
    private Stage authoringStage = new Stage();
    private ListView<String> list;

    public Menu(Stage stage) {
        myStage = stage;
        myRoot = new Pane();
        currentTheme = VoogaPeaches.getUser().getThemeName();

        myScene = new Scene(myRoot, WIDTH, HEIGHT);
        setupScene();

        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.setTitle("main.VoogaPeaches: Menu");
        myStage.show();
        myRoot.getStylesheets().add(VoogaPeaches.getUser().getThemeName());
        formatButtons();
        setupGames();
        updateTheme();
    }

    public void setupScene() {
        addButtons();
        addTitle();
    }

    private void setupGames() {
        double width = 200;
        double height = 150;
        double botMargin = 50;
        list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App", "Single", "Double",
                "Suite", "Family App", "Single", "Double", "Suite", "Family App");
        list.setItems(items);
        list.setLayoutX(WIDTH/2-width/2);
        list.setLayoutY(HEIGHT-height-botMargin);
        list.setPrefSize(width, height);
        myRoot.getChildren().add(list);
    }

    private void updateTheme() {
        myRoot.getStylesheets().add(currentTheme);
        PubSub.getInstance().publish("THEME_MESSGE",new StringMessage(VoogaPeaches.getUser().getThemeName()));
        PubSub.getInstance().subscribe(
                "THEME_MESSAGE",
                (message) -> {
                    if (myRoot.getStylesheets().size() >= 1) {
                        myRoot.getStylesheets().remove(0);
                    }
                    currentTheme = ((StringMessage) message).readMessage();
                    myRoot.getStylesheets().add(currentTheme);
                    VoogaPeaches.getUser().setTheme(currentTheme);
                }
        );
        myRoot.getStyleClass().add("panel");
    }

    private void onAuthoringPressed() {
        if (!authoringStage.isShowing()) {
            authoringStage.setTitle("main.VoogaPeaches: A Programmers for Peaches Production");
            authoringStage.setMaximized(true);
            authoringStage.setResizable(false);
            authoring = new Screen(authoringStage);
            authoringStage.setOnCloseRequest(event -> {
                authoring.save();
                DatabaseConnector<User> connector = new DatabaseConnector<>(User.class);
                try {
                    connector.addToDatabase(VoogaPeaches.getUser());
                } catch (ObjectIdNotFoundException e) {
                    //TODO: is this possible? If so what do?
                }
            });
        }
        else {
            //do nothing, only can have one authoring environment open at once
        }
        //myStage.close(); //TODO: keep the menu open! easier and then we only have one menu and do not have to make another
    }

    private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
        //http://docs.oracle.com/javafx/2/ui_controls/button.htm
        Button authoringButton = createMenuButton(AUTHORINGPIC, AUTHORING_ENVIRONMENT);
        Button playerButton = createMenuButton(PLAYERPIC, PLAYER);

        buttons = new ArrayList<>(Arrays.asList(authoringButton, playerButton));
        myRoot.getChildren().addAll(buttons);
        buttons.get(0).setOnAction((e) -> onAuthoringPressed());
        buttons.get(1).setOnAction((e) -> onPlayingPressed());
    }

    private void onPlayingPressed() {
        System.out.println("Implement Playing lol");
    }

    private void formatButtons() {
        int numButtons = buttons.size();

        double buttonXOffset = WIDTH/(numButtons+2);
        double buttonYOffset = HEIGHT*2/3;

        for (int i = 0; i < numButtons; i++) {
            setMenuButtonLayout(buttons.get(i), buttonXOffset*(i*2+1) - buttons.get(i).getBoundsInLocal().getWidth()/2, buttonYOffset);
        }
    }

    private void setMenuButtonLayout(Button button, double x, double y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    private Button createMenuButton(String imageName, String buttonText) {
        Button myButton = new Button();
        myButton.setGraphic(createImageView(imageName));

        myButton.setTooltip(new Tooltip(buttonText));

        return myButton;
    }

    private void addTitle() {
        ImageView title = createImageView("resources/menuImages/VoogaTransparent.png");
        title.setScaleX(0.75);
        title.setScaleY(0.75);
        title.setLayoutX(WIDTH / 2 - title.getBoundsInLocal().getWidth() / 2);
        title.setLayoutY(HEIGHT * 1 / 3 - title.getBoundsInLocal().getHeight() / 2);
        myRoot.getChildren().add(title);
    }

    private ImageView createImageView(String picLocation) {
        File myFile = new File(picLocation);
        ImageView myImageView = new ImageView(myFile.toURI().toString());
        return myImageView;
    }
}
