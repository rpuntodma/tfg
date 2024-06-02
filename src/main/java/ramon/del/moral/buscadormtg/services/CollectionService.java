package ramon.del.moral.buscadormtg.services;

import ramon.del.moral.buscadormtg.entities.CardModel;
import ramon.del.moral.buscadormtg.entities.CollectionModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CollectionService {

    List<CollectionModel> findAll();

    Optional<CollectionModel> findById(Long id);

    CollectionModel save(CollectionModel collection);

    void deleteById(Long id);

    Set<CardModel> findCardsByCollectionId(Long id);

    List<CollectionModel> findByUserId(Long userId);
}
