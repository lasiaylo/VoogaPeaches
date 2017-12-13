package scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.StateEvent
import engine.fsm.FSM
import engine.fsm.State
import util.pubsub.PubSub
import util.pubsub.messages.FSMMessage
import util.pubsub.messages.Message

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity
    FSM fsm = null
    String fsmName = "test"
    PubSub pubSub = PubSub.getInstance()

    pubSub.publish("FSM", new FSMMessage(fsmName, null))

    pubSub.subscribe("FSM", { Message msg ->
        FSMMessage message = (FSMMessage) msg
        if(message.getFsm() != null && fsmName.equals(message.getName())) {
            fsm = message.getFsm()
        }
    })

    entity.on(EventType.STEP.getType(),{ Event call ->
        if (fsm != null) {
            State newState = (State) fsm.step()
            StateEvent stateEvent = new StateEvent(newState)
            event.recursiveFire(entity)
        }
    })

}