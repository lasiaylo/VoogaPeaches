package authoring.menu;

import authoring.PanelController;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class GameSavePrompt {

    private static final String SAVE_GAME = "Save Game";
    public static final String SAVE_PROMPT = "Please enter your game's name:";
    private PanelController panelController;

    public GameSavePrompt(PanelController panelController) {
        this.panelController = panelController;
        Optional<String> result = createChoiceDialog().showAndWait();
        result.ifPresent(name -> { saveGame(name); });
    }

    private TextInputDialog createChoiceDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.getDialogPane().getButtonTypes().remove(0);
        dialog.getDialogPane().getButtonTypes().add(0, new ButtonType(SAVE_GAME, ButtonBar.ButtonData.OK_DONE));
        dialog.setTitle(SAVE_GAME);
        dialog.setContentText(SAVE_PROMPT);
        return dialog;
    }

    private void saveGame(String name) {
        if(name.trim().length() != 0)
            panelController.save(name.trim());
    }

}
