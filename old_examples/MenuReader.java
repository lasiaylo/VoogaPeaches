package frontend.xml;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exceptions.ErrorMessage;
import exceptions.XMLException;
import frontend.menus.CustomMenuButton;
import frontend.menus.strategies.iMenuItemStrategy;
import frontend.modules.ViewModule;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * A class that recursively reads in an xml file that defines the menus
 * This class is important because it allows the user to create any number of
 * submenus
 * @author Albert
 * 
 * I chose this class as my masterpiece because, while it does not necessarily implement any specific design patterns, I think that
 * this class and this feature is the best way to show that I completely understand the open to extension and closed to modification principle.
 * In essence, this class allows people to create any number of submenus within submenus within submenus, no matter how many levels you want to go down, without
 * changing any kind of code within the actual project besides adding a corresponding class for reflection. I feel that a lot of teams would write code that only reads menus
 * down to a certain level, but this menu reader will read down to any level, which makes it completely reusable.
 * 
 * All a user has to do is add an item into the xml file. In addition, this class is completely modular and is completely reusable, because the idea of
 * recursively reading in menus is not restricted to slogo.
 *
 */
public class MenuReader extends XMLReader {
	private static final String TYPE_TAG = "type";
	private static final String NAME_TAG = "name";
	private static final String ITEM_TAG = "item";
	private static final String MENU_TAG = "menu";
	private static final String STRATEGY_TAG = "strategy";
	private static final String PREFIX = "frontend.menus.strategies.";

	private Map<String, Menu> mySubMenus;

	/**
	 * Creates a new MenuReader and reads from file
	 * @param path			path of file to read
	 * @param viewModule	associated ViewModule
	 * @throws XMLException
	 * @throws IOException
	 */
	public MenuReader(String path, ViewModule viewModule) throws XMLException, IOException {
		super(path, viewModule);
	}

	@Override
	protected void readFromFile() throws XMLException {
		mySubMenus = new LinkedHashMap<>();
		NodeList nList = this.getNodeList(MENU_TAG);

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element menu = (Element) nNode;
				parseMenu(menu);
			}
		}
	}

	/**
	 * parses the top level menus, which can only be javafx Menus because of the way a MenuBar is set up
	 * @param menu
	 */
	protected void parseMenu(Element menu) {
		Node itemHead = menu.getFirstChild();
		NodeList subItems = menu.getChildNodes();
		String menuName = StringLevelParse(itemHead, subItems.getLength(), NAME_TAG);
		try {
			Menu newMenu = parseSubMenu(menu, itemHead, subItems.getLength());
			mySubMenus.put(menuName, newMenu);
		} catch(XMLException e) {
			ErrorMessage eMessage = new ErrorMessage("Could not read menus!");
			eMessage.show();
		}
	}

	/**
	 * Parses through the siblings of a node to find an xml element that matches the given tag
	 * @param head		Node that represents head of linkedlist of siblings
	 * @param length	length of linkedlist
	 * @param tag		tag to search for
	 * @return			read in string value whose xml identifier matches tag
	 * @throws XMLException
	 */
	private String StringLevelParse(Node head, int length, String tag) throws XMLException {
		Node current = head;
		for (int i = 0; i < length; i++) {
			if (current.getNodeType() == Node.ELEMENT_NODE) {
				if (current.getNodeName().equals(tag)) {
					return current.getTextContent();					
				}
			}
			current = current.getNextSibling();
		}

		throw new XMLException();
	}

	/**
	 * Creates a SubMenu by recursively reading in all items from the siblings of an initial node
	 * @param menu		Element that represents the root menu element in the menubar
	 * @param head		head of linkedlist of sibling nodes to read over
	 * @param length	length of above linkedlist
	 * @return			a recursively defined submenu
	 * @throws XMLException
	 */
	private Menu parseSubMenu(Element menu, Node head, int length) throws XMLException{
		Menu newMenu = new Menu(StringLevelParse(head, length, NAME_TAG));
		Node current = head;
		for (int j = 0; j < length; j++) {
			if(current == null) {
				continue;
			}
			
			if (current.getNodeType() == Node.ELEMENT_NODE) {
				Element menuItem = (Element) current;				
				if (current.getNodeName().equals(ITEM_TAG)) {
					MenuItem newItem = createMenuItem(menu, menuItem);
					newMenu.getItems().add(newItem);
				}
			}
			current = current.getNextSibling();
		}
		return newMenu;
	}

	/**
	 * Determines whether a given item shoudld be a menu or a button. If menu, recurse; if button, create button and return
	 * @param menu		Element that represents the root menu in the menubar
	 * @param menuItem	Element to be created that is either a button or a submenu
	 * @return			the correct MenuItem, whether it is button or menu
	 * @throws XMLException
	 */
	private MenuItem createMenuItem(Element menu, Element menuItem) throws XMLException {
		if (getContent(menuItem, TYPE_TAG).equals(MENU_TAG)) {
			int length = menuItem.getChildNodes().getLength();
			Node head = menuItem.getFirstChild();
			while(head.getNodeType() != Node.ELEMENT_NODE) {
				head = head.getNextSibling();
			}
			
			Menu subMenu = parseSubMenu(menuItem, head, length);
			return subMenu;
		} else {
			String name = getContent(menuItem, NAME_TAG);
			MenuItem newItem = new MenuItem(name);
			CustomMenuButton newCustomMenu = null;
			try {
				Class<?> cls = Class.forName(PREFIX + getContent(menuItem, STRATEGY_TAG));
				iMenuItemStrategy strategy = (iMenuItemStrategy) cls.getDeclaredConstructor(ViewModule.class)
						.newInstance(getViewModule()); 
				newCustomMenu = new CustomMenuButton(newItem, strategy);
				return newCustomMenu.getItem();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new XMLException();
			}
			
		}
	}
	
	/**
	 * @return	a map of menutext to Menus
	 */
	public Map<String, Menu> getSubMenus() {
		return mySubMenus;
	}
}
