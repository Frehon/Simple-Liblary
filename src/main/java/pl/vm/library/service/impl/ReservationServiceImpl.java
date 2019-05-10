package pl.vm.library.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.vm.library.Enum.ReservationType;
import pl.vm.library.entity.Reservation;
import pl.vm.library.exception.EntityExceptionService;
import pl.vm.library.exception.service.impl.ReservationExceptionService;
import pl.vm.library.repository.BookRepository;
import pl.vm.library.repository.ReservationRepository;
import pl.vm.library.repository.UserRepository;
import pl.vm.library.service.ReservationService;
import pl.vm.library.to.ReservationTo;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
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

    @Override
    public ReservationTo extend(ReservationTo reservation) {

        validateExistingReservation(reservation);
        Reservation reservationEntity = mapper.map(reservation, Reservation.class);

        reservationRepository.save(reservationEntity);

        return mapper.map(reservationEntity, ReservationTo.class);
    }

    private void validateNewReservation(ReservationTo reservation) {

        checkIfIdNotProvided(reservation.getId());
        checkReservationDates(reservation.getFromDate(), reservation.getToDate());
        checkIfBookExists(reservation.getBookId());
        checkIfUserExists(reservation.getUserId());
        checkIfThereAreNoOtherOverlappingReservations(reservation, ReservationType.NEW_RESERVATION);
    }

    private void validateExistingReservation(ReservationTo reservation) {

        checkIfIdProvided(reservation.getId());
        checkReservationDates(reservation.getFromDate(), reservation.getToDate());
        checkIfBookExists(reservation.getBookId());
        checkIfUserExists(reservation.getUserId());
        checkIfReservationExists(reservation);
        checkIfThereAreNoOtherOverlappingReservations(reservation, ReservationType.EXTENDING_EXISTING_RESERVATION);
    }

    private void checkIfIdProvided(Long reservationId) {

        if (reservationId == null) {
            reservationExceptionService.throwMissingIdException();
        }
    }

    private void checkIfIdNotProvided(Long reservationId) {

        if (reservationId != null) {
            reservationExceptionService.throwParameterValidationException();
        }
    }

    private void checkReservationDates(Date fromDate, Date toDate) {

        Date now = new Date();

        if (fromDate.before(now) || toDate.before(fromDate)) {
            reservationExceptionService.throwIncorrectDatesException();
        }
    }

    private void checkIfBookExists(Long bookId) {

        if (!bookRepository.findById(bookId).isPresent()) {
            bookExceptionService.throwEntityNotFoundException();
        }
    }

    private void checkIfUserExists(Long userId) {

        if (!userRepository.findById(userId).isPresent()) {
            userExceptionService.throwEntityNotFoundException();
        }
    }

    private void checkIfReservationExists(ReservationTo reservation) {

        if (!reservationRepository
                .findReservationByIdFromDateBookAndUser(reservation.getId(), reservation.getBookId(), reservation.getFromDate(), reservation.getUserId())
                .isPresent()) {
            reservationExceptionService.throwExistingReservationNotFoundException();
        }
    }

    private void checkIfThereAreNoOtherOverlappingReservations(ReservationTo reservation, ReservationType reservationType) {

        Set<Reservation> overlappingReservations = new HashSet<>();

        switch (reservationType) {
            case NEW_RESERVATION: {
                overlappingReservations =
                        reservationRepository.findOverlappingReservations(reservation.getBookId(), reservation.getFromDate(), reservation.getToDate());
                break;
            }
            case EXTENDING_EXISTING_RESERVATION: {
                overlappingReservations =
                        reservationRepository.findOverlappingReservations(reservation.getId(), reservation.getBookId(), reservation.getFromDate(), reservation.getToDate());
            }
        }

        if (!overlappingReservations.isEmpty()) {
            reservationExceptionService.throwBookReservedException(overlappingReservations);
        }
    }
}
