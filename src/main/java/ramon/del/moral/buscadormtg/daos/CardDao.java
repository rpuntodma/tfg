package ramon.del.moral.buscadormtg.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ramon.del.moral.buscadormtg.entities.CardModel;

import java.util.List;

public interface CardDao extends JpaRepository<CardModel, Long> {

    @Query("SELECT c.cards FROM CollectionModel c WHERE c.id = :collectionId")
    List<CardModel> findCardsByCollectionId(@Param("collectionId") Long collectionId);
}
