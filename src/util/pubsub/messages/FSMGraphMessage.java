package util.pubsub.messages;

import authoring.fsm.FSMGraph;

/**
 * Message used to communicate an FSMGraph between the FSMPanel and the actual creation of an FSM.
 *
 * @author Simran
 */
public class FSMGraphMessage extends Message {

    private FSMGraph graph;

    public FSMGraphMessage(FSMGraph graph) { this.graph = graph; }

    public FSMGraph getGraph() { return graph; }
}
