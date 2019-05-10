package pl.vm.library.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.vm.library.entity.Book;
import pl.vm.library.exception.service.impl.BookExceptionService;
import pl.vm.library.repository.BookRepository;
import pl.vm.library.service.BookService;
import pl.vm.library.to.BookTo;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookExceptionService bookExceptionService;

	private ModelMapper mapper = new ModelMapper();

	@Override
	public List<BookTo> findAll() {
		List<Book> books = (List<Book>) bookRepository.findAll();

		return books.stream()
				.map(bookEntity -> mapper.map(bookEntity, BookTo.class))
				.collect(Collectors.toList());
	}

	@Override
	public BookTo findById(Long id) {
		return bookRepository.findById(id)
				.map(userEntity -> mapper.map(userEntity, BookTo.class))
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public BookTo create(BookTo bookTo) {
		validateNewBook(bookTo);

		Book bookEntity = mapper.map(bookTo, Book.class);

		bookRepository.save(bookEntity);

		return mapper.map(bookEntity, BookTo.class);
	}

	@Override
	public void delete(Long bookId) {

	}

	private void validateNewBook(BookTo book) {
		if (book.getId() != null) {
			bookExceptionService.throwParameterValidationException();
		}
	}
}
