import engine.fsm.FSM;
import engine.fsm.Logic;
import engine.fsm.State;
import engine.fsm.Transition;

import java.util.HashMap;
import java.util.Map;

public class FSMTest {
    public static void main(String[] args) {
        State state1 = new State("state 1");
        String logic = "ramil == false";
        Map<String, Object> params = new HashMap<>();
        params.put("ramil", new Boolean(false));

        State state2 = new State("state 2");

        Transition transition = new Transition(state2);
        transition.setCondition(logic, params);

        state1.getTransitions().add(transition);
        FSM fsm = new FSM(state1, state1);

        System.out.println(fsm.getCurrentState().getType());
        fsm.update();
        System.out.println(fsm.getCurrentState().getType());
//        Logic logic = new Logic(myLogic, params);
//        System.out.println(logic.evaluate());

    }
}
