package pl.vm.library.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pl.vm.library.service.BookService;
import pl.vm.library.to.BookTo;

@RestController
@RequestMapping("/books")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookTo> findAll() {
		return bookService.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public BookTo findById(@PathVariable Long id) {
		return bookService.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookTo create(@Valid @RequestBody BookTo book) {
		return bookService.create(book);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable long id) {
		bookService.delete(id);
	}

}
