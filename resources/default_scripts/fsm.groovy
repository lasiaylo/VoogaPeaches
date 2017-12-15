package filedata.scripts

import authoring.fsm.FSMGraph
import database.jsonhelpers.JSONDataFolders
import database.jsonhelpers.JSONDataManager
import database.jsonhelpers.JSONToObjectConverter
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.StateEvent
import engine.fsm.FSM
import engine.fsm.State
import main.VoogaPeaches
import org.json.JSONArray
import org.json.JSONObject
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
            for (int i = 0; i < 10; ++i)
                fsm.step()
        }
    })


    print(VoogaPeaches.getIsGaming())
    if(VoogaPeaches.getIsGaming()) {
        JSONToObjectConverter<FSMGraph> graphConverter = new JSONToObjectConverter<>(FSMGraph.class)
        String filepath = VoogaPeaches.getGameID() + "/fsm.json"
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.GAMES)
        JSONObject jsonForm = manager.readJSONFile(filepath)
        println(filepath)
        println("form: " + jsonForm)
        println("UID: " + entity.UIDforObject())
        if(jsonForm.keySet().contains((entity.UIDforObject()))) {
            JSONArray tempJsonArray = jsonForm.getJSONArray(entity.UIDforObject())
            print(2)
            for(int n = 0; n < tempJsonArray.length(); n++)
            {
                print(2)
                JSONObject object = tempJsonArray.getJSONObject(n)
                FSMGraph graph = graphConverter.createObjectFromJSON(FSMGraph.class, object)
                if(graph.getMyName().equals(fsmName)) {
                    fsm = graph.createFSM(entity)
                }
            }
            print(2)
            for (int i = 0; i < 10; ++i)
                fsm.step()
        }
    } else if(fsm == null) {
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