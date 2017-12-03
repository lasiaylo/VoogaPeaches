package engine.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class ScriptFactory {
    private Collection<String> paths;

    private ScriptFactory() {
        paths = new HashSet<>();
        readDirectory();
    }

    public ScriptFactory(String[] paths) {
        this();
        add(paths);
    }

    public ScriptFactory(String path) {
        this();
        add(path);
    }

    public void add(String[] paths) {
        this.paths.addAll(Arrays.asList(paths));
    }

    public void add(String path) {
        paths.add(path);
    }

    private void readDirectory() {

    }
}
