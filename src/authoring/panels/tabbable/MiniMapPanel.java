package authoring.panels.tabbable;

import authoring.Panel;
import authoring.PanelController;
import engine.EntityManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.ErrorDisplay;
import util.math.num.Vector;

import java.util.*;

/**
 * panel that allows the user to jump around in the whole contiguous map and add levels (noncontiguous parts of the map)
 * @author Brian Nieves
 * @author Kelly Zhang
 * @author Estelle He
 */
public class MiniMapPanel implements Panel, MapChangeListener{

    private static final String CHANGE_NAME = "Change Name";
    private static final String DELETE = "Delete";
    private static final String PANEL = "panel";
    private static final String LEVEL_NAME = "Level Name";
    private static final String MAP_WIDTH_STRING = "Map Width";
    private static final String MAP_HEIGHT_STRING = "Map Height";
    private static final String ADD_LEVEL = "Add Level";
    private static final String LEVEL1 = "Level 1";
    private static final String LEVEL = "Level";
    private static final String DELETE_LEVEL = "Delete Level";
    private static final String CANNOT_DELETE_LEVEL_PROMPT = "Cannot delete current level";
    private static final String CHANGE_LEVEL_NAME = "Change Level Name";
    private static final String NEW_LEVEL_NAME = "New Level Name:";
    private static final String MAP_SIZE = "Map Size";
    private static final String NOT_INTEGER_ERROR = "Not an integer";
    private static final int PADDING = 15;
    private static final String MINI_MAP = "Mini Map";
    private static final int LEVELBAR_SPACING = 10;
    private static final int BOX_SPACING = 15;
    private static final int VALUE1 = 5000;
    private static final int VALUE2 = 5000;
    private static final String SELECT = "Select";
    private Pane myPane;
    private TextField levelName;
    private TextField mapWidth;
    private TextField mapHeight;
    private Button addLevel;
    private EntityManager manager;
    private HBox levelBar;
    private TableView<Map.Entry> levelTable;
    private ObservableList<Map.Entry> levelList;
    private ContextMenu menu;
    private MenuItem change;
    private MenuItem delete;
    private MenuItem select;
    private Text myLevel;
    private VBox box;

    public MiniMapPanel() {
        myPane = new Pane();
        levelList = FXCollections.observableList(new ArrayList<>());
        levelList.add(new Map.Entry() {
            @Override
            public Object getKey() {
                return LEVEL1;
            }

            @Override
            public Object getValue() {
                return new Vector(VALUE1, VALUE2);
            }

            @Override
            public Object setValue(Object value) {
                return null;
            }
        });
        myLevel = new Text("my level: ");

        myPane.getStyleClass().add(PANEL);
        setupTextFields();
        addLevel = new Button(ADD_LEVEL);

        levelBar = new HBox(levelName, mapWidth, mapHeight);
        levelBar.setSpacing(LEVELBAR_SPACING);
        levelBar.setAlignment(Pos.CENTER);

        menu = new ContextMenu();
        select = new MenuItem(SELECT);
        change = new MenuItem(CHANGE_NAME);
        delete = new MenuItem(DELETE);
        select.setOnAction(e -> select());
        change.setOnAction(e -> change());
        delete.setOnAction(e -> delete());
        menu.getItems().addAll(select, change, delete);

        levelTable = new TableView<>();
        levelTable.setItems(levelList);
        TableColumn<Map.Entry, String> levelT = new TableColumn<>(LEVEL);
        levelT.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue().getKey()));
        TableColumn<Map.Entry, String> widthT = new TableColumn<>(MAP_WIDTH_STRING);
        widthT.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(((Vector)cellData.getValue().getValue()).at(0).toString()));
        TableColumn<Map.Entry, String> heightT = new TableColumn<>(MAP_HEIGHT_STRING);
        heightT.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(((Vector)cellData.getValue().getValue()).at(1).toString()));
        levelTable.getColumns().setAll(levelT, widthT, heightT);
        levelTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        levelTable.setOnMouseClicked(e -> selectLevel(e));
        levelTable.setOnContextMenuRequested(e -> menu.show(levelTable, e.getScreenX(),e.getScreenY()));

        addLevel.setOnMouseClicked(e -> add());

        getRegion().getStyleClass().add(PANEL);
    }

    private void setupTextFields() {
        levelName = new TextField();
        levelName.setPromptText(LEVEL_NAME);
        mapWidth = new TextField();
        mapWidth.setPromptText(MAP_WIDTH_STRING);
        mapHeight = new TextField();
        mapHeight.setPromptText(MAP_HEIGHT_STRING);
    }

    private void delete() {
        if (manager.getCurrentLevelName().equals(levelTable.getSelectionModel().getSelectedItem().getKey())) {
            new ErrorDisplay(DELETE_LEVEL, CANNOT_DELETE_LEVEL_PROMPT).displayError();
        }
        else {
            manager.deleteLevel((String) levelTable.getSelectionModel().getSelectedItem().getKey());
        }
    }
    private void change() {
        TextInputDialog popup = new TextInputDialog();
        popup.setTitle(CHANGE_LEVEL_NAME);
        popup.setContentText(NEW_LEVEL_NAME);
        Optional<String> result = popup.showAndWait();
        result.ifPresent(name -> {
            manager.changeLevelName((String) levelTable.getSelectionModel().getSelectedItem().getKey(), name);
        });
    }

    /**
     * new method to switch levels, doesnt involve clicking on table which selects the level (which doesnt allow you to delete)
     */
    private void select() {
        manager.changeLevel((String) levelTable.getSelectionModel().getSelectedItem().getKey());
        box.getChildren().set(0, new Text("my level: " + (String) levelTable.getSelectionModel().getSelectedItem().getKey()));
    }

//    private void selectLevel(MouseEvent event) {
//        String selectL = null;
//        try {
//            selectL = (String) levelTable.
//                    getSelectionModel().
//                    getSelectedItem().
//                    getKey();
//        } catch (NullPointerException e) {
//            //TODO: There's nothing in the table, should we do any handling?
//        }
//        manager.changeLevel(selectL);
//    }

    private void add() {
        levelName.commitValue();
        mapWidth.commitValue();
        mapHeight.commitValue();
        if  (levelTable.getItems().size() == 1) {
//            manager.changeLevel((String) levelTable.getItems().get(0).getKey());
//            myLevel = new Text((String) levelTable.getItems().get(0).getKey());
        }
        try {
            int width = Integer.parseInt(mapWidth.getText());
            int height = Integer.parseInt(mapHeight.getText());
            manager.addLevel(levelName.getText(), width, height);
        }
        catch (NumberFormatException e){
            new ErrorDisplay(MAP_SIZE, NOT_INTEGER_ERROR).displayError();
        }
    }

    @Override
    public Region getRegion() {
        StackPane.setAlignment(myPane, Pos.CENTER);
        box = new VBox(myLevel, myPane, levelBar, addLevel, levelTable);
        box.setSpacing(BOX_SPACING);
        box.setPadding(new Insets(PADDING));
        box.setAlignment(Pos.CENTER);
        Pane holder = new StackPane();
        holder.getChildren().add(box);
        StackPane.setAlignment(box, Pos.CENTER);
        return holder;
    }

    @Override
    public void setController(PanelController controller) {
        myPane = (Pane) controller.getMiniMap();
        myPane.setCenterShape(true);
        manager = controller.getManager();
        manager.addMapListener(this);
    }

    @Override
    public String title(){
        return MINI_MAP;
    }

    @Override
    public void onChanged(Change change) {
        levelList.clear();
        change.getMap().entrySet().forEach(e -> levelList.add((Map.Entry) e));
    }
}