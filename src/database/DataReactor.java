package database;

/**
 * The interface used for creating lambdas that define what
 * action to perform within the DatabaseConnector when new
 * data is received
 */
public interface DataReactor<T> {
    /**
     * Specifies how to handle a new piece of data when it
     * comes in from the database
     * @param newData is an object of type T returned from
     *                the database that is to be handled
     */
    public void reactToNewData(T newObject);

    /**
     * Specifies how to handle when a piece of data moves
     * within the database
     * @param newData is an object of type T returned from
     *                the database
     */
    public void reactToDataMoved(T movedObject);

    /**
     * Specifies how to handle within a piece of data is
     * changed within the database
     * @param newData is an object of type T returned from
     *                the database
     */
    public void reactToDataChanged(T changedObject);

    /**
     * Specifies how to handle when a piece of data is
     * removed from the database
     * @param newData is an object of type T returned from
     *                the database
     */
    public void reactToDataRemoved(T removedObject);
}
