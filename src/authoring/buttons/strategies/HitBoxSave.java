package authoring.buttons.strategies;

import authoring.panels.tabbable.HitBoxPanel;

public class HitBoxSave implements IButtonStrategy {

    private HitBoxPanel hbPanel;

    public HitBoxSave(HitBoxPanel hbPanel) {
        this.hbPanel = hbPanel;
    }

    @Override
    public void fire() { }
}