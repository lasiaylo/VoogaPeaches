package database.fileloaders;

import groovy.lang.Closure;
import groovy.lang.GroovyShell;
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
 *
 * @author Ramil
 */
public class ScriptLoader {

    /* Final Variables */
    private static final String USER_SCRIPT_PATH = "./data/filedata/scripts/";
    private static final String DEFAULT_SCRIPT_PATH = "./resources/default_scripts/";

    private static Map<String, Closure> cachedScripts = new HashMap<>();

    public static void cache() {
        cacheScriptsAtPath(DEFAULT_SCRIPT_PATH);
        cacheScriptsAtPath(USER_SCRIPT_PATH);
    }

    /**
     * Caches all the scripts in the custom data grid_move directory recursively
     *
     * @param path is a {@code String} corresponding to the director to cache files in
     */
    private static void cacheScriptsAtPath(String path) {
        File directory = new File(path);
        Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[]{"groovy"}, true);
        File file;
        GroovyShell shell = new GroovyShell();
        while (iterator.hasNext() && (file = iterator.next()) != null)
            try {
                cachedScripts.put(file.getPath().substring(path.length()).replaceAll("\\\\", "/"), (Closure) shell.evaluate(readStringForFile(file)));
            } catch (Exception e) {}
    }

    /**
     * Reads in the strings for a groovy grid_move line by line, and
     * converts the grid_move into a single string that is then stored
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
            while ((line = br.readLine()) != null)
                scriptString = scriptString + line + "\n";
            br.close();
            return scriptString;
        } catch (IOException e) { return ""; }
    }

    /**
     * Retrieves the string with the groovy code that corresponds
     * to the passed in file name
     *
     * @param filename is a {@code String} that represents the name
     *                 of the Groovy Script that you want to retireve
     * @return A {@code String} containing the groovy code to be executed
     */
    public static Closure getScript(String filename) {
        return cachedScripts.get(filename + ".groovy");
    }
}
