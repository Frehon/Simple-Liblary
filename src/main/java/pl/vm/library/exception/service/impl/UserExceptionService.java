package pl.vm.library.exception.service.impl;

import org.springframework.stereotype.Service;
import pl.vm.library.exception.EntityExceptionService;
import pl.vm.library.exception.model.EntityWithProvidedIdNotFoundException;

@Service
public class UserExceptionService extends EntityExceptionService {

    @Override
    public void throwEntityNotFoundException() {
        throw new EntityWithProvidedIdNotFoundException("The User with the given ID was not found.");
    }
}
