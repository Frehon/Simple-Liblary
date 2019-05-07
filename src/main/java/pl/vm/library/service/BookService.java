package pl.vm.library.service;

import java.util.List;

import pl.vm.library.to.BookTo;

/**
 * The Service which contains business logic for Book.
 */
public interface BookService {

	/**
	 * Returns all Books.
	 * 
	 * @return all Books
	 */
	public List<BookTo> findAll();

	/**
	 * Returns the Book with the given ID.
	 * 
	 * @return the found Book
	 */
	public BookTo findById(Long id);

	/**
	 * Creates a new Entity for the given object.
	 * 
	 * @param BookTo
	 * @return the persisted Book
	 */
	public BookTo create(BookTo book);

	/**
	 * Deletes the Book with the given id.
	 * 
	 * @param BookTo
	 * @return the persisted Book
	 */
	public void delete(Long id);

}
