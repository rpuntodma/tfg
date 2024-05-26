package ramon.del.moral.buscadormtg.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import ramon.del.moral.buscadormtg.entities.CardModel;

public interface CardDao extends JpaRepository<CardModel, Long> {
}
