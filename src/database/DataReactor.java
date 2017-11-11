package database;

/**
 * The interface used to specify a class that is
 * capable of handling database events that will
 * return a new object of type T
 */
public interface DataReactor<T> {
    /**
     * Specifies how to handle a new piece of data when it
     * comes in from the database. NOTE: The object passed
     * to the method CAN BE NULL; it is up to the DataReactor
     * to handle this!
     * @param newObject is an object of type T returned from
     *                the database that is to be handled
     */
    public void reactToNewData(T newObject);

    /**
     * Specifies how to handle when a piece of data moves
     * within the database. NOTE: The object passed
     * to the method CAN BE NULL; it is up to the DataReactor
     * to handle this!
     * @param movedObject is an object of type T returned from
     *                the database
     */
    public void reactToDataMoved(T movedObject);

    /**
     * Specifies how to handle within a piece of data is
     * changed within the database. NOTE: The object passed
     * to the method CAN BE NULL; it is up to the DataReactor
     * to handle this!
     * @param changedObject is an object of type T returned from
     *                the database
     */
    public void reactToDataChanged(T changedObject);

    /**
     * Specifies how to handle when a piece of data is
     * removed from the database. NOTE: The object passed
     * to the method CAN BE NULL; it is up to the DataReactor
     * to handle this!
     * @param removedObject is an object of type T returned from
     *                the database
     */
    public void reactToDataRemoved(T removedObject);
}
