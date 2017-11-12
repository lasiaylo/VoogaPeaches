package engine.util;

/**A central manager that maintains game states for the frame
 * 
 * @author lasia
 *
 */
public interface IManager{
	/**User a tag and checks whether the condition is met
	 * @param Object to be used by the manager
	 * @param tag
	 * @return boolean
	 */
	boolean check(Object arg1,String tag);
}
