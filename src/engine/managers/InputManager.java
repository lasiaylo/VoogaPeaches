package engine.managers;


import com.google.gson.annotations.Expose;
import database.TrackableObject;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Class that manages and reads user key inputs
 * @author Albert
 */
public class InputManager extends TrackableObject implements IManager{
    @Expose private Map<KeyCode, String> keyCommandMap;
    @Expose private Map<String, Boolean> commandExecuteMap;

    /**
     *  Creates a new InputManager
     */
    private InputManager() {
        this(new HashMap<>());
    }

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

        // automatically removes
        commandExecuteMap.put(command, new Boolean(keyPressed));
    }

    public boolean check(Object commandTag) {
        return commandExecuteMap.getOrDefault(commandTag, false);
    }

    /**
     * Sets a user defined key press value to a user defined string command. Overrides previously mapped commands and
     * adds to properties file; adds new command if not available
     * @param code      KeyCode to be mapped
     * @param command   String command
     */
    public void setCommand(KeyCode code, String command) {
        keyCommandMap.put(code, command);
        if(!commandExecuteMap.containsKey(command)) {
            commandExecuteMap.put(command, new Boolean(false));
        }
    }

    /**
     * Removes a keycode mapped to a command.
     * @param code  keycode mapped to command to be removed
     */
    public void removeKeyMapping(KeyCode code) {
        String command = keyCommandMap.remove(code);
    }

    /**
     * Removes a command and all its associated keybindings
     * @param command   command to be removed
     */
    public void removeCommand(String command) {
        Iterator keyIter = keyCommandMap.keySet().iterator();
        while(keyIter.hasNext()) {
            KeyCode code = (KeyCode) keyIter.next();
            if(keyCommandMap.get(code).equals(command)) {
                keyIter.remove();
            }
        }

        commandExecuteMap.remove(command);
    }
}
