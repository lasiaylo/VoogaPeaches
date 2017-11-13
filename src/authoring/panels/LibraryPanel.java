package authoring.panels;

import java.io.File;
import java.util.ArrayList;

import authoring.Panel;
import authoring.Screen;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * simple library need further implementation
 * @author estellehe
 *
 */
public class LibraryPanel implements Panel{
	private static final String BG = "Background";
	private static final String PLAYER = "Player";
	private static final String PATH = "resources/graphics/";
	
	private TilePane myTilePane;
	private ChoiceBox<String> myEntType;
	
	public LibraryPanel() {
		myTilePane = new TilePane();
		myEntType = new ChoiceBox<String>();
		
		myEntType.getItems().addAll(BG, PLAYER);
		myEntType.setOnAction(e -> changeType());
		myTilePane.setPrefColumns(2);
		myTilePane.setPrefTileWidth(50);
		myTilePane.setPrefTileHeight(50);
		myTilePane.setHgap(10);
	}

	private void changeType() {
		String type = myEntType.getValue();
		ArrayList<ImageView> imageList = new ArrayList<ImageView>();
		//This is just a temporary implementation of library
		//Library should get image for entity from engine
		try {
			File imageFolder = new File(PATH + type);
			for (File each: imageFolder.listFiles()) {
				ImageView view = new ImageView(new Image(each.getName()));
				view.setFitWidth(50);
				view.setFitHeight(50);
				imageList.add(view);
			}
		}
		catch (NullPointerException e){
			// again this is not a permanent implementation so simply error print out
			System.out.println(e);
		}
		myTilePane.getChildren().clear();
		myTilePane.getChildren().addAll(imageList);
		System.out.println(imageList.size());
	}

	@Override
	public Region getRegion() {
		VBox box = new VBox(myEntType, myTilePane);
		box.setSpacing(10);
		return box;
	}

	@Override
	public int getArea() {
		return Screen.TOP_RIGHT;
	}

	@Override
	public String title() {
		return "Library";
	}

}
