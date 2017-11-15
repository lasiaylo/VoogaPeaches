package engine.managers;


import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;


/**
 * Class that manages and reads user key inputs
 * @author Albert
 */
public class InputManager implements IManager{
    private static InputManager instance;

    private Map<KeyCode, String> keyCommandMap;
    private Map<String, Boolean> commandExecuteMap;

    /**
     * Creates a new InputManager from the database
     */
    private InputManager(Map<String, String> commands) {
        keyCommandMap = new HashMap<>();
        for(String s : commands.keySet()) {
            keyCommandMap.put(KeyCode.getKeyCode(s), commands.get(s));
        }
        commandExecuteMap = new HashMap<>();
    }

    /**
     *  Creates a new InputManager
     */
    private InputManager() {
        this(new HashMap<>());
    }

    /**
     * Handles the user key input for command by accepting a keycode, determing whether or not
     * that keycode has been mapped to a command, and setting the command's tobeexecuted value to keyPressed
     * @param code          KeyCode pressed or released
     * @param keyPressed    true if key pressed, false if not
     */
    public void keyInputHandle(KeyCode code, boolean keyPressed) {
        String command = keyCommandMap.getOrDefault(code, null);
        if(command == null) {
            return;
        }

        commandExecuteMap.put(command, new Boolean(keyPressed));
    }

    public boolean check(Object commandTag) {
        return commandExecuteMap.getOrDefault(commandTag, false);
    }

    /**
     * Sets a user defined key press value to a user defined string command. Overrides previously mapped commands and
     * adds to properties file
     * @param code      KeyCode to be mapped
     * @param command   String command
     */
    public void setCommand(KeyCode code, String command) {
        keyCommandMap.put(code, command);
        // TO DO Write to properties file
    }

    /**
     * Removes a previously defined command mapped to passed in keycode. Removes from properties file
     * @param code  keycode mapped to command to be removed
     */
    public void removeCommand(KeyCode code) {
        String command = keyCommandMap.remove(code);
        commandExecuteMap.remove(command);
        // TO DO remove from properties file
    }

    /**
     * Implements the Singleton design pattern
     * @return  a single instance of InputManager
     */
    public static InputManager getInstance() {
        if(instance == null) {
            instance = new InputManager();
        }

        return instance;
    }
}
