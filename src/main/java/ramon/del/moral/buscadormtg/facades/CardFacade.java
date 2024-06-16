package ramon.del.moral.buscadormtg.facades;

import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CardFacade {

    List<CardDto> findAll();

    Optional<CardDto> findById(Long id);

    CardDto save(CardDto card, CollectionDto collection);

    void deleteById(Long id);

    List<CardDto> searchCardsByName(String name) throws IOException, InterruptedException;

    Set<CardDto> findAllByCollection(Long collectionId);
}
