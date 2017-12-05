package authoring.panels.attributes;

import java.util.Map;

/**A Setter that sets a particular value in a Map
 * @author lasia
 *
 */
public class MapSetter implements Setter{
	private Map<Object,Object> myMap;
	private Object myKey;
	
	public MapSetter(Map<Object,Object> map, Object key) {
		myMap = map;
		myKey = key;
	}

	@Override
	public Object getValue() {
		return myMap.get(myKey);
	}

	@Override
	public void setValue(Object arg) {
		myMap.put(myKey, arg);
	}

}
