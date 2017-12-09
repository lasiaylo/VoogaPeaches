package authoring.panels.attributes;

import javafx.scene.layout.VBox;

import java.util.Map;

public class ScriptProperties {
    private VBox myVBox;

    public ScriptProperties(Map<String,Map<String,Object>> map){
        for (String name : map.keySet()){
            myVBox.getChildren().add(
                    addChildPane(name, makeParameters(map.get(name))))
            )
        }
    }
}
