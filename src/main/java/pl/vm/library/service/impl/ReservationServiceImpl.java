package pl.vm.library.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.vm.library.entity.Reservation;
import pl.vm.library.exception.EntityExceptionService;
import pl.vm.library.exception.model.ParameterValidationException;
import pl.vm.library.exception.service.impl.ReservationExceptionService;
import pl.vm.library.repository.BookRepository;
import pl.vm.library.repository.ReservationRepository;
import pl.vm.library.repository.UserRepository;
import pl.vm.library.service.ReservationService;
import pl.vm.library.to.ReservationTo;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationExceptionService reservationExceptionService;

    @Autowired
    @Qualifier("bookExceptionService")
    private EntityExceptionService bookExceptionService;

    @Autowired
    @Qualifier("userExceptionService")
    private EntityExceptionService userExceptionService;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public ReservationTo create(ReservationTo reservation) {

        validateNewReservation(reservation);
        Reservation reservationEntity = mapper.map(reservation, Reservation.class);

        reservationRepository.save(reservationEntity);

        return mapper.map(reservationEntity, ReservationTo.class);
    }

    // TODO Extend reservation - change the "toDate" Date in the given reservation

    private void validateNewReservation(ReservationTo reservation) {

        Long reservationId = reservation.getId();
        Long bookId = reservation.getBookId();
        Long userId = reservation.getUserId();

        validateReservationId(reservationId);
        checkIfBookExist(bookId);
        checkIfUserExist(userId);
        checkIfBookIsNotReserved(reservation);
    }

    private void validateReservationId(Long reservationId) {
        if (reservationId != null) {
            throw new ParameterValidationException("When creating new Reservation, the ID should be null.");
        }
    }

    private void checkIfBookExist(Long bookId) {
        if (!bookRepository.findById(bookId).isPresent()) {
            bookExceptionService.throwEntityNotFoundException();
        }
    }

    private void checkIfUserExist(Long userId) {
        if (!userRepository.findById(userId).isPresent()) {
            userExceptionService.throwEntityNotFoundException();
        }
    }

    private void checkIfBookIsNotReserved(ReservationTo reservation) {

        Long bookId = reservation.getBookId();
        Date fromDate = reservation.getFromDate();
        Date toDate = reservation.getToDate();

        Set<Reservation> conflictingReservations = reservationRepository.findOverlappingReservations(bookId, fromDate, toDate);
        if (!conflictingReservations.isEmpty()) {
            reservationExceptionService.throwBookReservedException(conflictingReservations);
        }
    }
}
