package pl.vm.library.repository;

import org.springframework.data.repository.CrudRepository;

import pl.vm.library.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
