package authoring.panels.attributes;


/**Sets/gets a field inside an object
 * @author lasia
 *
 */
public interface Setter {
	
	/**Gets the desired field inside the object
	 * @return Object
	 */
	public abstract Object getValue();
	
	/**Sets the desired field inside the object
	 * 
	 */
	public abstract void setValue(Object arg);
	
	
	
}
