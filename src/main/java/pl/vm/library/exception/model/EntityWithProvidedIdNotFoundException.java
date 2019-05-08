package pl.vm.library.exception.model;

/**
 * Exception used when trying to operate on Entities which don't exist in the
 * Database.
 */
public class EntityWithProvidedIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 518405525882996153L;

	public EntityWithProvidedIdNotFoundException() {
		super("The Entity with the given ID was not found.");
	}

	public EntityWithProvidedIdNotFoundException(String message) {
		super(message);
	}
}
