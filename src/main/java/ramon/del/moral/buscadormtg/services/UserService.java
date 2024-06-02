package ramon.del.moral.buscadormtg.services;

import ramon.del.moral.buscadormtg.entities.CollectionModel;
import ramon.del.moral.buscadormtg.entities.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(Long id);

    UserModel save(UserModel userModel);

    void deleteById(Long id);
}
