package authoring.panels.tabbable;


import authoring.Panel;
import authoring.PanelController;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class HUDPanel implements Panel {
    private HBox hBox;

    public HUDPanel(){
        hBox = new HBox();
        hBox.setMouseTransparent(false);
    }

    @Override
    public Region getRegion() {
        return hBox;
    }

    @Override
    public String title() {
        return "HUD";
    }

    @Override
    public void setController(PanelController controller){
        setView( (Group) controller.getHUD());
    }

    private void setView(Group hud) {
        hBox.getChildren().add(hud);
    }
}
