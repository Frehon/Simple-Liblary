package pl.vm.library.service.impl;

import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.vm.library.entity.Book;
import pl.vm.library.entity.Reservation;
import pl.vm.library.exception.model.EntityWithProvidedIdNotFoundException;
import pl.vm.library.exception.model.ExistingReservationException;
import pl.vm.library.exception.service.impl.BookExceptionService;
import pl.vm.library.repository.BookRepository;
import pl.vm.library.repository.ReservationRepository;
import pl.vm.library.service.BookService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookExceptionService bookExceptionService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void shouldDeleteBook() {

        // given
        Optional<Book> bookOptional = createTestBook();
        Book book = bookOptional.get();
        Long bookId = book.getId();

        doNothing().when(bookRepository).delete(book);
        when(bookRepository.findById(bookId)).thenReturn(bookOptional);
        when(reservationRepository.findAllByBook(book)).thenReturn(Collections.emptySet());
        mockExceptions();

        // when
        bookService.delete(bookId);

        // then
        verify(bookRepository, Mockito.times(1)).findById(bookId);
        verify(reservationRepository, Mockito.times(1)).findAllByBook(book);
        verify(bookRepository, Mockito.times(1)).delete(book);
        verify(bookExceptionService, Mockito.never()).throwEntityNotFoundException();
        verify(bookExceptionService, Mockito.never()).throwReservationsForBookExistException();
    }

    @Test
    public void shouldThrowBookNotFoundException() {

        // given
        String exceptionMessage = "The Book with the given ID was not found.";
        Optional<Book> bookOptional = createTestBook();
        Book book = bookOptional.get();
        Long bookId = book.getId();

        doNothing().when(bookRepository).delete(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(reservationRepository.findAllByBook(book)).thenReturn(Collections.emptySet());
        mockExceptions();

        // when
        try {
            bookService.delete(bookId);
            fail("Expected BookNotFoundException to be thrown");
        } catch (EntityWithProvidedIdNotFoundException e) {
            // then
            assertThat(e.getMessage()).isEqualTo(exceptionMessage);
            verify(bookRepository, Mockito.times(1)).findById(bookId);
            verify(reservationRepository, never()).findAllByBook(book);
            verify(bookRepository, never()).delete(book);
            verify(bookExceptionService, times(1)).throwEntityNotFoundException();
            verify(bookExceptionService, never()).throwReservationsForBookExistException();
        }
    }

    @Test
    public void shouldThrowReservationExistException() {

        // given
        String exceptionMessage = "The Book is reserved. You can not delete it.";
        Optional<Book> bookOptional = createTestBook();
        Book book = bookOptional.get();
        Long bookId = book.getId();

        doNothing().when(bookRepository).delete(book);
        when(bookRepository.findById(bookId)).thenReturn(bookOptional);
        when(reservationRepository.findAllByBook(book)).thenReturn(Sets.newHashSet(Arrays.asList(new Reservation())));
        mockExceptions();

        // when
        try {
            bookService.delete(bookId);
            fail("Expected ExistingReservationException to be thrown");
        } catch (ExistingReservationException e) {
            // then
            assertThat(e.getMessage()).isEqualTo(exceptionMessage);
            verify(bookRepository, times(1)).findById(bookId);
            verify(reservationRepository, times(1)).findAllByBook(book);
            verify(bookRepository, never()).delete(book);
            verify(bookExceptionService, never()).throwEntityNotFoundException();
            verify(bookExceptionService, times(1)).throwReservationsForBookExistException();
        }
    }

    private Optional<Book> createTestBook() {
        Book book = new Book();
        book.setId(1L);
        return Optional.of(book);
    }

    private void mockExceptions() {
        doCallRealMethod().when(bookExceptionService).throwEntityNotFoundException();
        doCallRealMethod().when(bookExceptionService).throwReservationsForBookExistException();
    }
}
