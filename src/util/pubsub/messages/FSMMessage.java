package util.pubsub.messages;

import engine.entities.Entity;
import engine.fsm.FSM;

/**
 * @author Simran
 */
public class FSMMessage extends Message {

    private String name;
    private Entity entity;
    private FSM fsm;

    public FSMMessage(String name, Entity entity, FSM fsm) {
        this.name = name;
        this.entity = entity;
        this.fsm = fsm;
    }

    public String getName() {
        return name;
    }

    public FSM getFSM() { return fsm; }

    public Entity getEntity() { return entity; }
}
