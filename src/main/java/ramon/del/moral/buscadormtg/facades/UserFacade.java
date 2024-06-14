package ramon.del.moral.buscadormtg.facades;

import ramon.del.moral.buscadormtg.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserFacade {

    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    UserDto save(UserDto userDto);

    void deleteById(Long id);
}
