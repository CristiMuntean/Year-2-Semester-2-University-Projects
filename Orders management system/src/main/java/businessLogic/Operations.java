package businessLogic;

import presentation.Controller;

public interface Operations<T> {
    /**
     * <p>
     *     Abstract method for inserting a new row in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    public abstract int insert(Controller controller);
    /**
     * <p>
     *     Abstract method for editing a row in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    public abstract boolean edit(Controller controller);
    /**
     * <p>
     *     Abstract method for finding a row in a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return an object resembling the row that was found and null if no row was found
     */
    public abstract T find(Controller controller);

    /**
     * <p>
     *     Abstract method for removing a row from a table of the database
     * </p>
     * @param controller the controller object that has access to the gui
     * @return true if operation was successful and false if not
     */
    public abstract boolean remove(Controller controller);
}
