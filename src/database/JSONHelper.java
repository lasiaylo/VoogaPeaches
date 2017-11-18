package database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class that helps with conversion between objects, JSON, and maps
 *
 * @author Walker Willetts
 */
public class JSONHelper {

    /**
     * Creates a GSON for use with the rest of the class
     * @return a {@code GSON} object with particular settings
     */
    private static Gson createGSON() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }

    /**
     * Safely converts from generic object stored in json to
     * to an object for a map
     * @param obj is a {@code Object} representing the object from JSON to
     *            convert for the map
     * @return An {@code Object} representing the safely converted object
     */
    private static Object convertToSafeObject(Object obj) {
        if(obj.getClass() == JSONObject.class) {
            return mapFromJSON((JSONObject) obj);
        } else if(obj.getClass() == JSONArray.class) {
            return mapFromJSONArray((JSONArray) obj);
        }
        return obj;
    }

    /**
     * Creates a map from a JSONArray so that it can be stored
     * within database
     * @param arr is a {@code JSONArray} representing the JSONArray
     *            to convert to a map for database storage
     * @return A {@code Map<String,Object>} with the indexes of the original
     * array as the key, and their corresponding object as the values
     */
    private static Map<String, Object> mapFromJSONArray(JSONArray arr) {
        Map<String, Object> map = new HashMap<>();
        for(int i = 0; i < arr.length(); i++)
            map.put("" + i, convertToSafeObject(arr.get(i)));
        return map;
    }

    /**
     * Creates a JSONObject for the passed in object
     * @param obj is a {@code Object} representing the object to convert
     *            to a JSONObject
     * @return A {@code JSONObject} representing the converted
     * form of the passed in object
     */
    public static JSONObject JSONForObject(Object obj) {
        Gson creator = createGSON();
        return new JSONObject(creator.toJson(obj));
    }

    /**
     * creates a map form of the JSONObject that is passed into the function
     * @param jsonObj is a {@code JSONObject} representing the object to make
     *                the map from
     * @return A {@code Map<String,Object>} that contains the converted JSONObject
     */
    public static Map<String, Object> mapFromJSON(JSONObject jsonObj) {
        Map<String, Object> jsonPairs = new HashMap<>();
        for(String s : jsonObj.keySet()) {
            Object obj = convertToSafeObject(jsonObj.get(s));
            jsonPairs.put(s,obj);
        }
        return jsonPairs;
    }

    /**
     * Creates a map containing the key,value pairs of the json form of the
     * passed in object
     * @param obj is an {@code Object} representing the object to convert
     * @return A {@code Map<String, Object>} that contains the key,value pairs
     * of the JSON object
     */
    public static Map<String, Object> jsonMapFromObject(Object obj) {
        JSONObject objInJSON = JSONForObject(obj);
        return mapFromJSON(objInJSON);
    }

}
