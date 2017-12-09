package authoring.panels.tabbable;

import authoring.Panel;
import authoring.PanelController;
import engine.EntityManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import util.ErrorDisplay;
import util.math.num.Vector;

import java.util.*;

public class MiniMapPanel implements Panel, MapChangeListener{

    private Pane myPane;
    private TextField levelName;
    private TextField mapWidth;
    private TextField mapHeight;
    private Button addLevel;
    private EntityManager manager;
    private HBox levelBar;
    private TableView<Map.Entry> levelTable;
    private ObservableList<Map.Entry> levelList;
    private TextField newName;
    private HBox bar;

    public MiniMapPanel() {
        myPane = new Pane();
        levelList = FXCollections.observableList(new ArrayList<>());
        levelList.add(new Map.Entry() {
            @Override
            public Object getKey() {
                return "level 1";
            }

            @Override
            public Object getValue() {
                return new Vector(5000, 5000);
            }

            @Override
            public Object setValue(Object value) {
                return null;
            }
        });

        myPane.getStyleClass().add("panel");
        levelName = new TextField("level name");
        mapWidth = new TextField("map width");
        mapHeight = new TextField("map height");
        newName = new TextField("new name");
        addLevel = new Button("add level");

        levelBar = new HBox(levelName, mapWidth, mapHeight);
        levelBar.setSpacing(10);
        levelBar.setAlignment(Pos.CENTER);
        bar = new HBox(addLevel, newName);
        bar.setSpacing(10);
        bar.setAlignment(Pos.CENTER);

        levelTable = new TableView<>();
        levelTable.setItems(levelList);
        TableColumn<Map.Entry, String> levelT = new TableColumn<>("Level");
        levelT.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue().getKey()));
        TableColumn<Map.Entry, String> widthT = new TableColumn<>("Map Width");
        widthT.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(((Vector)cellData.getValue().getValue()).at(0).toString()));
        TableColumn<Map.Entry, String> heightT = new TableColumn<>("Map Height");
        heightT.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(((Vector)cellData.getValue().getValue()).at(1).toString()));
        levelTable.getColumns().setAll(levelT, widthT, heightT);
        levelTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        levelTable.setOnMouseClicked(e -> selectLevel());

        addLevel.setOnMouseClicked(e -> add());
        newName.setOnKeyPressed(e -> change(e));


    }

    private void change(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            newName.commitValue();
            manager.changeCurrentLevelName(newName.getText());
            newName.setText("new name");
        }
    }

    private void selectLevel() {
        String selectL = (String) levelTable.getSelectionModel().getSelectedItem().getKey();
        manager.changeLevel(selectL);
    }

    private void add() {
        levelName.commitValue();
        mapWidth.commitValue();
        mapHeight.commitValue();
        try {
            //check for the width and height
            int width = Integer.parseInt(mapWidth.getText());
            int height = Integer.parseInt(mapHeight.getText());
            manager.addLevel(levelName.getText(), width, height);
        }
        catch (NumberFormatException e){
            new ErrorDisplay("Map Size", "Not an integer").displayError();
        }
        levelName.setText("level name");
        mapWidth.setText("map width");
        mapHeight.setText("map height");
    }

    @Override
    public Region getRegion() {
        VBox box = new VBox(myPane, levelBar, bar, levelTable);
        box.setSpacing(15);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        Pane holder = new StackPane();
        holder.getChildren().add(box);
        StackPane.setAlignment(box, Pos.CENTER);
        return holder;
    }

    @Override
    public void setController(PanelController controller) {

        myPane = controller.getMiniMap();
        myPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        myPane.setCenterShape(true);
        manager = controller.getManager();
        manager.addMapListener(this);

    }

    @Override
    public String title(){
        return "Mini Map";
    }


    @Override
    public void onChanged(Change change) {
        levelList.clear();
        change.getMap().entrySet().forEach(e -> levelList.add((Map.Entry) e));
    }
}
