package pl.vm.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.vm.library.entity.Book;
import pl.vm.library.entity.Reservation;

import java.util.Date;
import java.util.Optional;
import java.util.Set;


public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    /**
     * Finding overlapping reservations for given book and time period
     *
     * @param bookId
     * @param fromDate
     * @param toDate
     * @return Set of reservations which prevent for saving the new one
     */
    @Query("select reservation from Reservation reservation " +
            "join reservation.book book on book.id = :bookId " +
            "where :fromDate between reservation.fromDate and reservation.toDate " +
            "or :toDate between reservation.fromDate and reservation.toDate")
    Set<Reservation> findOverlappingReservations(@Param("bookId") Long bookId,
                                                 @Param("fromDate") Date fromDate,
                                                 @Param("toDate") Date toDate);

    /**
     * Finding overlapping reservations for given book and time period
     *
     * @param reservationId
     * @param bookId
     * @param fromDate
     * @param newToDate
     * @return Set of reservations which prevent for extending the existing one
     */
    @Query("select reservation from Reservation reservation " +
            "join reservation.book book on book.id = :bookId " +
            "where reservation.id <> :reservationId " +
            "and reservation.fromDate between :fromDate and :newToDate")
    Set<Reservation> findOverlappingReservations(@Param("reservationId") Long reservationId,
                                                 @Param("bookId") Long bookId,
                                                 @Param("fromDate") Date fromDate,
                                                 @Param("newToDate") Date newToDate);


    /**
     * Check if exist a reservation for given parameters
     *
     * @param reservationId
     * @param book
     * @param fromDate
     * @param user
     * @return reservation which match given criteria otherwise Optional.empty
     */
    @Query("select reservation from Reservation reservation " +
            "join reservation.book book on book.id = :bookId " +
            "join reservation.user user on user.id = :userId " +
            "where reservation.id = :reservationId " +
            "and reservation.fromDate = :fromDate")
    Optional<Reservation> findReservationByIdFromDateBookAndUser(@Param("reservationId") Long reservationId,
                                                                 @Param("bookId") Long book,
                                                                 @Param("fromDate") Date fromDate,
                                                                 @Param("userId") Long user);

    /**
     * Finding all reservation for given book
     *
     * @param book
     * @return Set of reservations of given book
     */
    Set<Reservation> findAllByBook(Book book);
}
