package pl.vm.library.service.impl;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.vm.library.repository.ReservationRepository;
import pl.vm.library.service.ReservationService;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	private ModelMapper mapper = new ModelMapper();

	// TODO Create reservation.

	// TODO Extend reservation - change the "toDate" Date in the given reservation

}
