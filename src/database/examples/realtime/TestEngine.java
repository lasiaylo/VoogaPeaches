package database.examples.realtime;

import database.firebase.DataReactor;
import database.firebase.DatabaseConnector;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import util.exceptions.ObjectIdNotFoundException;

public class TestEngine implements DataReactor<Post> {

    /**
     * Test class that provides an example for how the realtime database is used
     */

    public static void main(String[] args) {

        TestEngine engine = new TestEngine();
        DatabaseConnector<Post> db = new DatabaseConnector<>(Post.class);
        JSONDataManager manager = new JSONDataManager(JSONDataManager.JSONDataFolders.IMAGES);

        // Setups the methods defined for the DataReactor to be
        // called on data events
        db.listenToChanges(engine);

        // Adds and removes a new post to the database
        Post newPost = new Post("New Post for demo","Walker", 5);
        Post secondPost = new Post("second post", "cox", 102);
        try {
            db.addToDatabase(newPost);
            db.addToDatabase(secondPost);
            db.removeFromDatabase(newPost);
            manager.writeJSONFile("postOne", JSONHelper.JSONForObject(newPost));

            // Have to force a sleep to wait for data to finish sending, but
            // with actual project this shouldn't be a problem
            Thread.sleep(1000);
        } catch (ObjectIdNotFoundException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    /* Methods required by the DataReactor Interface */
    @Override
    public void reactToNewData(Post newObject) {
        if(newObject != null) System.out.println("New Post:\n" + newObject.toString());
    }

    @Override
    public void reactToDataMoved(Post movedObject) {
        if(movedObject != null) System.out.println("Moved Post:\n" + movedObject.toString());

    }

    @Override
    public void reactToDataChanged(Post changedObject) {
        if(changedObject != null) System.out.println("Changed Post:\n" + changedObject.toString());
    }

    @Override
    public void reactToDataRemoved(Post removedObject) {
        if(removedObject != null) System.out.println("Removed Post:\n" + removedObject.toString());
    }

}
