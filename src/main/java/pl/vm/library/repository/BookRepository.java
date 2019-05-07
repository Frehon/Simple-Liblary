package pl.vm.library.repository;

import org.springframework.data.repository.CrudRepository;

import pl.vm.library.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
