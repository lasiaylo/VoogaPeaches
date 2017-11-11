package database;

public class TestEngine implements DataReactor<Post> {

    /**
     * TEST CLASS FOR DATABASE
     */

    public static void main(String[] args) {
        TestEngine e = new TestEngine();
        DatabaseConnector db = new DatabaseConnector<Post>(Post.class);
        db.listenToChanges(e);
        try {
            Thread.sleep(4000);
        } catch (Exception l) {

        }
    }

    @Override
    public void reactToNewData(Post newObject) {
        System.out.println("New Post:\n" + newObject.toString());
    }

    @Override
    public void reactToDataMoved(Post movedObject) {
        System.out.println("Moved Post:\n" + movedObject.toString());

    }

    @Override
    public void reactToDataChanged(Post changedObject) {
        System.out.println("Changed Post:\n" + changedObject.toString());

    }

    @Override
    public void reactToDataRemoved(Post removedObject) {
        System.out.println("Removed Post:\n" + removedObject.toString());
    }
}
