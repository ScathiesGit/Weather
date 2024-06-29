package git.scathiesgit.weather.repository.exception;

public class LocationAlreadyExistsException extends RuntimeException {

    public LocationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
