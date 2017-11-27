package database.jsonhelpers;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Class for managing the local storage, manipulation, and retrieval of JSON
 * data information for the project
 *
 * @author Walker Willetts
 */
public class JSONDataManager {

    /* Instance Variables */
    private String baseFolder;

    /**
     * Enum defining the different folders in the data folder of the
     * project where json files can be read and written from by the
     * JSONDataManager class
     */
    public enum DataFolders {
        GAMES ("games"),
        IMAGES ("images"),
        SCRIPTS ("scripts"),
        USER_SETTINGS ("user_settings");

        private final String filepath;
        DataFolders(String path) { this.filepath = "./data/" + path + "/"; }

        /**
         * @return A {@code String} representing the path of the folder within the project
         */
        String path() { return filepath; }
    }


    /**
     * Creates a new JSONDataManager that is able to manipulate
     * files within the given folder within the data folder
     * @param folder is a {@code DataFolders} enum value that
     *               specifies which of the subfolders in data
     *               where you want to manipulate JSON files
     */
    public JSONDataManager(DataFolders folder) {
        baseFolder = folder.path();
    }

    /**
     * Writes the specified JSONObject to the file specified. Note: If the
     * file is already present then the data in obj will overwrite the data
     * currently stored within the specified JSON file.
     *
     * @param filename is a {@code String} that represents the name of the file
     *                 to append the JSONObject on to, and if the file does not
     *                 have a .json extension then one will be appended.
     * @param obj is a {@code JSONObject} that represents the JSON you want to
     *            write to the given filename
     * @return {@code true} if the object is successfully written, and {@code false}
     * otherwise
     */
    public boolean writeJSONFile(String filename, JSONObject obj) {
        // Add json extension if not already present
        if(!filename.contains(".json")) filename = filename + ".json";
        try {
            FileWriter writer = new FileWriter(baseFolder + filename);
            writer.write(obj.toString(2));
            writer.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes the specified JSON file
     * @param filename is a {@code String} representing the name of the file to
     *                 be deleted within the base folder
     * @return {@code true} if the file was successfully deleted, and {@code false}
     * otherwise
     */
    public boolean deleteJSONFile(String filename) {
        if(!filename.contains(".json")) filename = filename + ".json";
        // Get the path to the file
        Path p = (new File(baseFolder + filename)).toPath();
        try {
            return Files.deleteIfExists(p);
        } catch(IOException e) {
            return false;
        }
    }

    /**
     * Reads in the JSON object from the file specified and returns it
     * @param filename is a {@code String} representing the filename to
     *                 read the JSON from
     * @return A {@code JSONObject} that contains the information stored
     * in the specified json file
     */
    public JSONObject readJSONFile(String filename) {
        if(!filename.contains(".json")) filename = filename + ".json";
        BufferedReader br = null;
        String json = "";
        try {
            String line;
            br = new BufferedReader(new FileReader(baseFolder + filename));
            while((line = br.readLine()) != null) {
                json += line + "\n";
            }
        } catch (Exception e) {
            return null;
        }
        return new JSONObject(json);
    }
}