package ramon.del.moral.buscadormtg.facades;

import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CollectionFacade {

    List<CollectionDto> findAll();

    Optional<CollectionDto> findById(Long id);

    CollectionDto save(CollectionDto collectionDto);

    void deleteById(Long id);
}
