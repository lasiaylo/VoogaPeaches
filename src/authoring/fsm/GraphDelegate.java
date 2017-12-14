package authoring.fsm;

public interface GraphDelegate {

    void removeMyself(StateRender state);

    void removeMyself(Arrow arrow);

    Arrow findArrowWith(String code);

    StateRender findStateRenderWith(String name);
}
