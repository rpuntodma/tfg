package ramon.del.moral.buscadormtg.facades;

import ramon.del.moral.buscadormtg.dtos.CardDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CardFacade {

    List<CardDto> findAll();

    Optional<CardDto> findById(Long id);

    CardDto save(CardDto card);

    void deleteById(Long id);

    List<CardDto> searchCardsByName(String name) throws IOException, InterruptedException;
}
