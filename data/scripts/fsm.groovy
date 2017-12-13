package scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.StateEvent
import engine.fsm.FSM
import engine.fsm.State
import util.pubsub.PubSub

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity
    FSM fsm = null
    String fsmName = "test"
    PubSub pubSub = PubSub.getInstance()
    
    entity.on(EventType.STEP.getType(),{ Event call ->
        if (fsm != null) {
            State newState = (State) fsm.step()
            StateEvent stateEvent = new StateEvent(newState);
            event.recursiveFire(entity);
        }
    })

}