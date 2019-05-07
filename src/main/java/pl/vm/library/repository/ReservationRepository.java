package pl.vm.library.repository;

import org.springframework.data.repository.CrudRepository;

import pl.vm.library.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

}
