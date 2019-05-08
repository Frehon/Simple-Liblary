package pl.vm.library.service;

import pl.vm.library.to.ReservationTo;

/**
 * The Service which contains business logic for Reservation.
 */
public interface ReservationService {

    ReservationTo create(ReservationTo reservation);

	// TODO Extend reservation - change the "toDate" Date in the given reservation
}
