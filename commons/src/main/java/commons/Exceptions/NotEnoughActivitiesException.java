package commons.Exceptions;

public class NotEnoughActivitiesException extends Exception {

    public NotEnoughActivitiesException() {

    }

    public NotEnoughActivitiesException(String message) {
        super (message);
    }

    public NotEnoughActivitiesException(Throwable cause) {
        super (cause);
    }

    public NotEnoughActivitiesException(String message, Throwable cause) {
        super(message, cause);
    }
}