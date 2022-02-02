package filmAPI.interfaces;

import java.sql.Connection;

public interface DbInterface {

    /**
     * Method to connect to the DataBase
     */
    void connect();

    /**
     * Method to get the Connection object of the DataBase
     *
     * @return if the connection exists return the Connection object, otherwise null
     */
    Connection getConnection();
}