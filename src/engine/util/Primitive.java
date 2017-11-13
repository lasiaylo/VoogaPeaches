package engine.util;
import java.util.*;

//Source: https://stackoverflow.com/questions/709961/determining-if-an-object-is-of-primitive-type
/**Converts Wrapper Class into its respective Primitive
 * Java's wrapper classes apparently don't implement an wrapper interface
 * @author lasia
 *
 */
public class Primitive{
	private static final Map<Class<?> , Class<?>> WRAPPER_MAP = getWrapperMap();

    
    public static Class<?> getPrimitive(Class<?> clazz){
    	return WRAPPER_MAP.get(clazz);
    }
    
    private static Map< Class<?> , Class<?> > getWrapperMap(){
    	Map< Class<?> , Class<?> > ret = new HashMap< Class<?> , Class<?> > ();
    	ret.put(Boolean.class, Boolean.TYPE);
    	ret.put(Character.class,Character.TYPE);
    	ret.put(Byte.class, Byte.TYPE);
    	ret.put(Short.class, Short.TYPE);
    	ret.put(Long.class, Long.TYPE);
    	ret.put(Float.class, Float.TYPE);
    	ret.put(Double.class,Double.TYPE);
    	ret.put(Void.class,Void.TYPE);
    	ret.put(Integer.class,Integer.TYPE);
    	
    	return ret;
    }

    public static boolean isWrapper(Class<?> clazz)
    {
    	return WRAPPER_MAP.containsKey(clazz);
    }
}