package authoring.menuactions;

import authoring.MenuAction;
import authoring.menu.GameSavePrompt;

import authoring.PanelController;

/**
 * Defines the MenuAction that saves the game currently being worked on in the authoring environment.
 */
public class SaveAction implements MenuAction{
    private final PanelController panelController;

    /**
     * Creates a new SaveAction executor.
     * @param panelController the controller to communicate with
     */
    public SaveAction(PanelController panelController){
        this.panelController = panelController;
    }

    @Override
    public void execute(){
        GameSavePrompt save = new GameSavePrompt(panelController);
    }
}
