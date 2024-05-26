package ramon.del.moral.buscadormtg.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import ramon.del.moral.buscadormtg.entities.CollectionModel;

public interface CollectionDao extends JpaRepository<CollectionModel, Long> {
}
