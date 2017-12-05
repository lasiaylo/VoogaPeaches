import engine.fsm.Logic;
import engine.fsm.State;

import java.util.HashMap;
import java.util.Map;

public class FSMTest {
    public static void main(String[] args) {
        State state1 = new State("state 1");
        String myLogic = new String("ramil == false");
        Map<String, Object> params = new HashMap<>();
        params.put("ramil", new Boolean(false));

        Logic logic = new Logic(myLogic, params);
        System.out.println(logic.evaluate());

    }
}
