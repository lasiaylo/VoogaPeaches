package util.pubsub.messages;

import authoring.fsm.FSMGraph;
import engine.entities.Entity;

import java.util.List;
import java.util.Map;

public class FSMSaveMessage extends Message {

    private Map<Entity, List<FSMGraph>> FSMmap;

    public FSMSaveMessage(Map<Entity, List<FSMGraph>> FSMmap) {
        this.FSMmap = FSMmap;
    }

    public Map<Entity, List<FSMGraph>> getFSMmap() { return FSMmap; }
}
