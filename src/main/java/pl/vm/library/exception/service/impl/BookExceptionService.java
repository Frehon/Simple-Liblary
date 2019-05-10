package pl.vm.library.exception.service.impl;

import org.springframework.stereotype.Service;
import pl.vm.library.exception.EntityExceptionService;
import pl.vm.library.exception.model.EntityWithProvidedIdNotFoundException;
import pl.vm.library.exception.model.ExistingReservationException;
import pl.vm.library.exception.model.ParameterValidationException;

/**
 * Service that handle all exceptions related with book
 */
@Service
public class BookExceptionService extends EntityExceptionService {

    @Override
    public void throwEntityNotFoundException() {
        throw new EntityWithProvidedIdNotFoundException("The Book with the given ID was not found.");
    }

    public void throwParameterValidationException() {
        throw new ParameterValidationException("When creating new Book, the ID should be null.");
    }

    public void throwIdNotProvidedException(){
        throw new ParameterValidationException("To delete a book, the ID needs to be provided.");
    }

    public void throwReservationsForBookExistException() {
        throw new ExistingReservationException("The Book is reserved. You can not delete it.");
    }
}
