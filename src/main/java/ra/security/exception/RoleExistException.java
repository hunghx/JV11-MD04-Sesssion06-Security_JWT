package ra.security.exception;

public class RoleExistException extends RuntimeException {
    public RoleExistException(String message) {
        super(message);
    }
}
