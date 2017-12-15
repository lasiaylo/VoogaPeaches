package util.pubsub.messages;

import authoring.fsm.FSMGraph;

public class FSMGraphMessage extends Message {

    private FSMGraph graph;

    public FSMGraphMessage(FSMGraph graph) { this.graph = graph; }

    public FSMGraph getGraph() { return graph; }
}
