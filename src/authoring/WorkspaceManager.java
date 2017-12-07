package authoring;

import authoring.panels.PanelManager;
import authoring.panels.reserved.CameraPanel;
import javafx.scene.layout.Pane;
import util.Loader;
import util.PropertiesReader;
import util.pubsub.PubSub;
import util.pubsub.messages.WorkspaceChange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * WorkspaceManager loads and handles the workspaces available for use.
 * @author Brian Nieves
 */
public class WorkspaceManager {

    private Pane currentWorkspaceArea;
    private Map<String, Workspace> workspaces = new HashMap<>();
    private PanelManager panelManager;
    private CameraPanel cameraPanel;

    /**
     * Creates a new workspace manager and initializes the workspaces.
     * @author Brian Nieves
     * @param workspaceArea the area of the screen to display the current workspace in
     * @param panelManager the manager of the panels to display in the workspace
     * @param cameraPanel the camera panel to add to the workspaces manually
     * @throws IOException if the workspace directory cannot be found
     */
    public WorkspaceManager(Pane workspaceArea, PanelManager panelManager, CameraPanel cameraPanel) throws IOException {
        currentWorkspaceArea = workspaceArea;
        this.panelManager = panelManager;
        this.cameraPanel = cameraPanel;

        createWorkspaces(workspaceArea.minWidthProperty().doubleValue(), workspaceArea.minHeightProperty().doubleValue());

        PubSub.getInstance().subscribe(
                "WORKSPACE_CHANGE",
                message -> switchWorkspace(((WorkspaceChange)message).readMessage()
        ));
    }

    /**
     * Returns the names of all of the workspaces that have been loaded.
     * @return a set of workspace names
     */
    public Set<String> getWorkspaces(){
        return workspaces.keySet();
    }

    /**
     * Saves all of the loaded workspace settings to their respective files.
     */
    public void saveWorkspaces(){
        for(Workspace workspace : workspaces.values()){
            workspace.save();
        }
    }

    /**
     * Loads all of the workspaces in the workspaces directory.
     * @param width the width of the workspace
     * @param height the height of the workspaces
     * @throws IOException if the workspaces could not be loaded or the directory does not exist
     */
    private void createWorkspaces(double width, double height) throws IOException{
        Map<String, Object> workspaces = Loader.loadObjects(
                PropertiesReader.value("reflect", "workspacepath"),
                width, height, panelManager);
        for(String space : workspaces.keySet()){
            Workspace workspace = (Workspace) workspaces.get(space);
            this.workspaces.put(space, workspace);
        }
        switchWorkspace(PropertiesReader.value("screenlayout", "currentworkspace"));
    }

    /**
     * Changes the workspace currently being viewed on the screen.
     * @param newWorkspace the name of the new workspace
     */
    private void switchWorkspace(String newWorkspace){
        Workspace workspace = workspaces.get(newWorkspace);
        workspace.addCameraPanel(cameraPanel.getRegion());
        currentWorkspaceArea.getChildren().clear();
        currentWorkspaceArea.getChildren().add(workspace.getWorkspace());
    }
}
