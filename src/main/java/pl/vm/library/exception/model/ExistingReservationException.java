package pl.vm.library.exception.model;

/**
 * Exception used when user try to reserve book when is already reserved for overlapping time period
 */
public class ExistingReservationException extends RuntimeException {
    public ExistingReservationException(String message) {
        super(message);
    }

}
