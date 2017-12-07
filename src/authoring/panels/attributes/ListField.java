package authoring.panels.attributes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;

public class ListField extends Field {
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
                filePath = file.getAbsolutePath();
                System.out.println(filePath);
                OBList.add(filePath);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

	@Override
	protected void getDefaultValue() {
		List<String> list = (ArrayList<String>) getValue(); 
		OBList = FXCollections.observableArrayList(list);
		listview.setItems(OBList);
	}

}
