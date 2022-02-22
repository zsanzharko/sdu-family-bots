package kz.sdu.exception;

public class ConnectionDatabaseJPAException extends Exception {
    private final static String lostDatabaseConnection =
            """
            Have problem with connection database, please try to check lost connection.
            Have problem with internet connection, please try to check lost connection.    
            """;

    public ConnectionDatabaseJPAException() {
        super(lostDatabaseConnection);
    }

    public ConnectionDatabaseJPAException(String message) {
        super(message);
    }
}
