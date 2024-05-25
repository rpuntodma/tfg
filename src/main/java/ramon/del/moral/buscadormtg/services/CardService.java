package ramon.del.moral.buscadormtg.services;

import org.springframework.stereotype.Service;
import ramon.del.moral.buscadormtg.entities.CardModel;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<CardModel> findAll();

    Optional<CardModel> findById(Long id);

    CardModel save(CardModel card);

    void deleteById(Long id);
}
