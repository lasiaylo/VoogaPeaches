package util.pubsub.messages;

import authoring.fsm.FSMGraph;

import java.util.ArrayList;
import java.util.Map;

/**
 * This needs to be an ArrayList and not a generic List.
 *
 * @author Simran
 */
public class FSMSaveMessage extends Message {

    private Map<String, ArrayList<FSMGraph>> FSMmap;

    public FSMSaveMessage(Map<String, ArrayList<FSMGraph>> FSMmap) {
        this.FSMmap = FSMmap;
    }

    public Map<String, ArrayList<FSMGraph>> getFSMmap() { return FSMmap; }
}
