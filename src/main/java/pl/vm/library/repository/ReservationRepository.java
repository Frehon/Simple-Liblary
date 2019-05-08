package pl.vm.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import pl.vm.library.entity.Reservation;

import java.util.Date;
import java.util.Set;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query("select reservation from Reservation reservation " +
            "join reservation.book book on book.id = :bookId " +
            "where :fromDate between reservation.fromDate and reservation.toDate " +
            "or :toDate between reservation.fromDate and reservation.toDate")
    Set<Reservation> findOverlappingReservations(@Param("bookId") Long bookId,
                                                 @Param("fromDate") Date fromDate,
                                                 @Param("toDate") Date toDate);
}
