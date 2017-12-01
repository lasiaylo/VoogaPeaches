package engine.managers;

/**A central manager that maintains game states for the frame
 * 
 * @author lasia
 *
 */
public interface IManager{
	/**Uses an object and checks whether the condition is met
	 * @param object Object to be checked by the manager
	 * @return boolean
	 */
	boolean check(Object object);
}
