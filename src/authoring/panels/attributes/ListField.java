package authoring.panels.attributes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListField extends Field {

    private static final String GROOVY_SCRIPTS_PROMPT = "Groovy Scripts";
    private static final String GROOVY_EXTENSION = "*.groovy";
    private static final String GROOVY = ".groovy";
	private ListView<String> listview;
	private ObservableList<String> OBList;

	public ListField(Setter setter) {
		super(setter);
	}

	@Override
	protected void makeControl() {
		listview = new ListView<String>();
		setControl(listview);
	}

	@Override
	protected void setControlAction() {
		listview.setOnKeyPressed(e-> delete(e));
		listview.setOnDragOver(e->handle(e));
		listview.setOnDragDropped(e->setControl(e));
		listview.setOnMouseClicked((e-> open()));
	}

	private void open() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter groovy = new FileChooser.ExtensionFilter(GROOVY_SCRIPTS_PROMPT, GROOVY_EXTENSION);
		fileChooser.getExtensionFilters().add(groovy);
		File selected = fileChooser.showOpenDialog(null);
		if (selected != null){
			addFileToList(selected);
		}
	}

	private void delete(KeyEvent e) {
		if (e.getCode() == KeyCode.DELETE) {
			String selected = listview.getSelectionModel().getSelectedItem();
			listview.getItems().remove(selected);
		}
	}

	public void handle(DragEvent event) {
		Dragboard db = event.getDragboard();
		if (db.hasFiles())
			event.acceptTransferModes(TransferMode.COPY);
		else
			event.consume();
     }
	 
	public void setControl(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String filePath = null;
            for (File file:db.getFiles()) {
				addFileToList(file);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

	private void addFileToList(File file) {
		String filePath = file.getAbsolutePath();
		if (filePath.endsWith(GROOVY)) {
            filePath = file.getName();
            OBList.add(filePath);
        }
	}

	@Override
	protected void getDefaultValue() {
		List<String> list = (ArrayList<String>) getValue(); 
		OBList = FXCollections.observableArrayList(list);
		listview.setItems(OBList);
	}
}