package util.pubsub.messages;

import engine.fsm.FSM;

/**
 * @author Simran
 */
public class FSMMessage extends Message {

    private String name;
    private FSM fsm;

    public FSMMessage(String name, FSM fsm) {
        this.name = name;
        this.fsm = fsm;
    }

    public String getName() {
        return name;
    }

    public FSM getFsm() {
        return fsm;
    }
}
