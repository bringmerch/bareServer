package core;

public class BareException extends Exception {
    private final int statusCode;

    public BareException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BareException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
