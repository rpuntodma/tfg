package ramon.del.moral.buscadormtg.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ramon.del.moral.buscadormtg.entities.CardModel;
import ramon.del.moral.buscadormtg.entities.CollectionModel;

import java.util.Set;

public interface CollectionDao extends JpaRepository<CollectionModel, Long> {

    @Query("SELECT c.cards FROM CollectionModel c WHERE c.id = :collectionId")
    Set<CardModel> findCardsByCollectionId(@Param("collectionId") Long collectionId);
}
