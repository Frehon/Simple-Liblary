package pl.vm.library.exception.service.impl;

import org.springframework.stereotype.Service;
import pl.vm.library.entity.Reservation;
import pl.vm.library.exception.EntityExceptionService;
import pl.vm.library.exception.model.EntityWithProvidedIdNotFoundException;
import pl.vm.library.exception.model.ExistingReservationException;
import pl.vm.library.exception.model.ParameterValidationException;

import java.util.Set;

@Service
public class ReservationExceptionService extends EntityExceptionService {

    public void throwBookReservedException(Set<Reservation> conflictingReservations) {
        StringBuilder message =
                new StringBuilder("Can not reserve the book, because someone else already did it for this time period. Please choose period which not overlap following date ranges: ");
        conflictingReservations.forEach(reservation -> message.append(reservation.getFromDate() + " till " + reservation.getToDate()));
        throw new ExistingReservationException(message.toString());
    }

    @Override
    public void throwEntityNotFoundException() {
        throw new EntityWithProvidedIdNotFoundException("The Reservation with the given ID was not found.");
    }

    public void throwParameterValidationException() {
        throw new ParameterValidationException("When creating new Reservation, the ID should be null.");
    }

    public void throwIncorrectDatesException() {
        throw new ParameterValidationException("Wrong dates parameters.");
    }

    public void throwMissingIdException() {
        throw new ParameterValidationException("When updating Reservation, the ID is required.");
    }

    public void throwExistingReservationNotFoundException() {
        throw new ParameterValidationException("Reservation for given data does not exist.");
    }
}
