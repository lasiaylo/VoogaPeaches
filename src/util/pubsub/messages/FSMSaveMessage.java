package util.pubsub.messages;

import authoring.fsm.FSMGraph;

import java.util.Map;
import java.util.Set;

public class FSMSaveMessage extends Message {

    private Map<String, Set<FSMGraph>> FSMmap;

    public FSMSaveMessage(Map<String, Set<FSMGraph>> FSMmap) {
        this.FSMmap = FSMmap;
    }

    public Map<String, Set<FSMGraph>> getFSMmap() { return FSMmap; }
}
