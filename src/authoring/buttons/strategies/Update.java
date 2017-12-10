package authoring.buttons.strategies;

import authoring.panels.attributes.Updatable;

public class Update implements IButtonStrategy {

    private Updatable myUpdate;

    public Update(Updatable update){
        myUpdate = update;
    }

    @Override
    public void fire() {
        myUpdate.update();
    }
}