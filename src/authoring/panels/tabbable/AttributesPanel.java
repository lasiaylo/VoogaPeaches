package authoring.panels.tabbable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import authoring.Panel;
import authoring.panels.attributes.Attribute;
import authoring.panels.attributes.Field;
import authoring.panels.attributes.FieldFactory;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import util.exceptions.GroovyInstantiationException;

/**Displays the attributes associated with a particular Entity
 * @author lasia
 *
 */
public class AttributesPanel implements Panel {
	private final String TITLE = "Properties";
	private final int LABEL_COL = 0;
	private final int ATTRIBUTE_COL = 1;
	private GridPane myGrid;
	private Map<String, Object> myMap;

	@Override
	public Region getRegion() {
		return myGrid;
	}
	
	@Override
	public String title() {
		return TITLE;
	}
	
	/**Takes in an entity and displays its properties
	 * @param entity
	 * @throws GroovyInstantiationException 
	 */
	public void updateProperties(Entity entity) throws GroovyInstantiationException {
		myGrid = new GridPane();
		myMap = entity.getProperties();
		addMap();
//		addButton();
	}


	private void addMap() throws GroovyInstantiationException {
		int row = 0;
		for (String s : myMap.keySet()) {
			myGrid.add(addLabel(s), LABEL_COL, row);
			myGrid.add(addAttribute(s), ATTRIBUTE_COL, row);
			row++;
		}
		
	}

	private Label addLabel(String string) {
		return new Label(string);
	}

	private Node addAttribute(String key) throws GroovyInstantiationException {
		Field field = FieldFactory.makeField(myMap, key);
		return field.getControl();
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

}
