package ramon.del.moral.buscadormtg.services;

import ramon.del.moral.buscadormtg.entities.CollectionModel;

import java.util.List;
import java.util.Optional;

public interface CollectionService {

    List<CollectionModel> findAll();

    Optional<CollectionModel> findById(Long id);

    CollectionModel save(CollectionModel collection);

    void deleteById(Long id);
}
