package database.fileloaders;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A class for caching Groovy scripts created by the user so
 * that unnecessary I/O is not constantly being performed,
 * which would waste resources
 */
public class ScriptLoader {

    /* Final Variables */
    private static final String SCRIPT_PATH = "./data/scriptdata/";
    private static final Map<String, String> CACHED_SCRIPTS = cache(SCRIPT_PATH);

    /**
     * Loads in the different Groovy files and saves them in a HashMap
     *
     * @return {@code Map<String, String>} of loaded groovy script strings
     * corresponding to the name of a groovy script
     */
    private static Map<String, String> loadAndCacheScripts() {
        Map<String, String> loadedFiles = new HashMap<>();
        File baseDirectory = new File(SCRIPT_PATH);
        for (File script : baseDirectory.listFiles()) {
            String scriptString = readStringForFile(script);
            loadedFiles.put(script.getName(), scriptString);
        }
        return loadedFiles;
    }

    /**
     * Caches all the scripts in a directory recursively
     *
     * @param path: path to file
     * @return {@code Map<String, String>} of loaded groovy script strings
     * corresponding to the name of a groovy script
     */
    private static Map<String, String> cache(String path) {
        Map<String, String> cache = new HashMap<>();
        File directory = new File(path);
        Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[]{"groovy"}, true);
        File file;

        while (iterator.hasNext() && (file = iterator.next()) != null)
            cache.put(file.getPath().substring(path.length()), "{ event, entity -> \n" + readStringForFile(file) + " }");

        return cache;
    }

    /**
     * Reads in the strings for a groovy script line by line, and
     * converts the script into a single string that is then stored
     *
     * @param groovyFile is a {@code File} representing the Groovy file
     *                   to read from
     * @return A {@code String} that contains all of the instructions
     */
    private static String readStringForFile(File groovyFile) {
        String scriptString = "";
        String line = null;
        try {
            FileReader fr = new FileReader(groovyFile);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                scriptString = scriptString + line + "\n";
            }
            br.close();
            return scriptString;
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Retrieves the string with the groovy code that corresponds
     * to the passed in file name
     *
     * @param filename is a {@code String} that represents the name
     *                 of the Groovy Script that you want to retireve
     * @return A {@code String} containing the groovy code to be executed
     */
    public static String stringForFile(String filename) {
        return CACHED_SCRIPTS.get(filename);
    }
}
