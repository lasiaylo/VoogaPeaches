package engine.entities;

import database.firebase.DatabaseConnector;
import util.ErrorDisplay;

public class EntitySubstituter {

    protected static Entity substitute(Entity old, Entity substitute) {
        try {
            DatabaseConnector.removeFromDatabasePath(getDbPath(old));
            old.stopTrackingTrackableObject(old.UIDforObject());
            substitute.replaceUID(old.UIDforObject());
            DatabaseConnector.addToDatabasePath(substitute, getDbPath(substitute));
            if(old.getParent() != null)
                old.getParent().remove(old);
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorDisplay("Data Error", "Could not connect to database").displayError();
        }
        try {
            old.getParent().getNodes().getChildren().remove(old.getNodes());
        } catch(NullPointerException e){
            // do nothing
        }

        if(!old.getChildren().isEmpty())
            for(Entity child : old.getChildren())
                substitute.add(child.substitute());

        substitute.initialize();
        return substitute;
    }

    private static String getDbPath(Entity old) {
        // Case: Set already
        if(old.getDbPath() != null) return old.getDbPath();
        // Case: Parent is root and it's set already
        if(old.getParent() == null) return "games/" + old.UIDforObject() + "/";
        // Case: Other
        String basePath = old.getDbPath() + "children/";
        int childIndex= 0;
        for(Entity child : old.getParent().getChildren()) {
            // Break once you get the right index
            if(old == child) break;
            childIndex++;
        }
        old.setDbPath(basePath + childIndex + "/");
        return old.getDbPath();
    }
}
