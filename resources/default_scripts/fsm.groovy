package filedata.scripts

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
    String fsmName = "squirtle"
    PubSub pubSub = PubSub.getInstance()

    println("fired boi")

    pubSub.subscribe("FSM", { Message msg ->
        FSMMessage message = (FSMMessage) msg
        if(message.getFSM() != null) {
            print("got fsm boi")
            fsm = message.getFSM()
        }
    })

    if(fsm == null) {
        pubSub.publish("FSM", new FSMMessage(fsmName, entity, null))
    }

    entity.on(EventType.STEP.getType(),{ Event call ->
        if (fsm != null) {
            State newState = (State) fsm.step()
            StateEvent stateEvent = new StateEvent(newState)
            stateEvent.recursiveFire(entity.getRoot())
        }
    })

}