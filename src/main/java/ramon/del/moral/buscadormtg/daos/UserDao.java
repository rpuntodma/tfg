package ramon.del.moral.buscadormtg.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import ramon.del.moral.buscadormtg.entities.UserModel;

public interface UserDao extends JpaRepository<UserModel, Long> {
}
