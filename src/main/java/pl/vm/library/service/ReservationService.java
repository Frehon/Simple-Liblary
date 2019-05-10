package pl.vm.library.service;

import pl.vm.library.to.ReservationTo;

/**
 * The Service which contains business logic for Reservation.
 */
public interface ReservationService {

    /**
     * Creates new Reservation
     * @param reservation
     * @return reservation transport object
     */
    ReservationTo create(ReservationTo reservation);

    /**
     * Extend existing reservation's time range
     * @param reservation
     * @return reservation transport object
     */
    ReservationTo extend(ReservationTo reservation);

}
