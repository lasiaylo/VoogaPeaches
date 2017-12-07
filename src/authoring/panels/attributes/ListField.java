package authoring.panels.attributes;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ListField extends Field {
	private ListView<String> listview;
	
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
		listview.setOnKeyPressed(e-> update(e));
	}

	private void update(KeyEvent e) {
		if (e.getCode() == KeyCode.DELETE)
			delete();
	}

	private void delete() {
		String selected = listview.getSelectionModel().getSelectedItem();
		listview.getItems().remove(selected);
	}

	@Override
	protected void getDefaultValue() {
		List<String> list = (ArrayList<String>) getValue(); 
		ObservableList<String> OBList = FXCollections.observableArrayList(list);
		listview.setItems(OBList);
	}

}
