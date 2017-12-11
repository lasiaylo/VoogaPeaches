package authoring.fsm;

import authoring.panels.attributes.Updatable;

public class UpdatableObject implements Updatable {
    @Override
    public void update() {
        System.out.println("Updating");
    }
}
