package pl.vm.library.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.vm.library.entity.User;
import pl.vm.library.exception.EntityWithProvidedIdNotFoundException;
import pl.vm.library.exception.ParameterValidationException;
import pl.vm.library.repository.UserRepository;
import pl.vm.library.service.UserService;
import pl.vm.library.to.UserTo;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private ModelMapper mapper = new ModelMapper();

	@Override
	public List<UserTo> findAll() {
		List<User> allUsers = (List<User>) userRepository.findAll();

		return allUsers.stream()
				.map(userEntity -> mapper.map(userEntity, UserTo.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserTo findById(Long id) {
		return userRepository.findById(id)
				.map(userEntity -> mapper.map(userEntity, UserTo.class))
				.orElseThrow(EntityWithProvidedIdNotFoundException::new);
	}

	@Override
	public UserTo create(UserTo userTo) {
		validateNewUser(userTo);

		User userEntity = mapper.map(userTo, User.class);
		userEntity = userRepository.save(userEntity);

		return mapper.map(userEntity, UserTo.class);
	}

	private void validateNewUser(UserTo userTo) {
		if (userTo.getId() != null) {
			throw new ParameterValidationException("When creating new User, the ID should be null.");
		}

		if (CollectionUtils.isNotEmpty(userTo.getReservations())) {
			throw new ParameterValidationException(
					"When creating new User the Reservation list should be initially empty.");
		}
	}

}
