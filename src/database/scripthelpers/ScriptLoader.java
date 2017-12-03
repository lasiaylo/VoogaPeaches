package database.scripthelpers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScriptLoader {

    private static final Map<String, String> CACHED_SCRIPTS = loadAndCacheScripts();
    private static final String SCRIPT_PATH = "./data/scriptdata/";

    private static Map<String, String> loadAndCacheScripts() {
        Map<String, String> loadedFiles = new HashMap<>();
        File baseDirectory = new File(SCRIPT_PATH);
        for(File script : baseDirectory.listFiles()) {
            String scriptString = readStringForFile(script);
            loadedFiles.put(script.getName(), scriptString);
        }
        return loadedFiles;
    }

    private static String readStringForFile(File groovyFile) {
        String scriptString = "";
        String line = null;
        try {
            FileReader fr = new FileReader(groovyFile);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null) {
                scriptString = scriptString + line + "\n";
            }
            br.close();
            return scriptString;
        } catch (IOException e) {
            return "";
        }
    }

    public static String stringForFile(String filename) {
        return CACHED_SCRIPTS.get(filename);
    }

    public static void main(String[] args) {
        System.out.println(stringForFile("example.groovy"));
    }

}
