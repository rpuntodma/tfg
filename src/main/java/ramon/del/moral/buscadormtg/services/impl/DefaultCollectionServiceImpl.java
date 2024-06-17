package ramon.del.moral.buscadormtg.services.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ramon.del.moral.buscadormtg.daos.CollectionDao;
import ramon.del.moral.buscadormtg.entities.CardModel;
import ramon.del.moral.buscadormtg.entities.CollectionModel;
import ramon.del.moral.buscadormtg.services.CollectionService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultCollectionServiceImpl implements CollectionService {

    @Resource
    CollectionDao collectionDao;

    @Override
    public List<CollectionModel> findAll() {
        return collectionDao.findAll();
    }

    @Override
    public Optional<CollectionModel> findById(Long id) {
        return collectionDao.findById(id);
    }

    @Override
    public CollectionModel save(CollectionModel collection) {
        return collectionDao.save(collection);
    }

    @Override
    public void deleteById(Long id) {
        collectionDao.deleteById(id);
    }

    @Override
    public Set<CardModel> findCardsByCollectionId(Long id) {
        return collectionDao.findById(id)
                            .orElseThrow()
                            .getCards();
    }

    @Override
    public List<CollectionModel> findByUserId(Long userId) {
        return collectionDao.findAll()
                            .stream()
                            .filter(collectionModel -> Objects.equals(collectionModel.getUser()
                                                                                     .getId(), userId))
                            .collect(Collectors.toList());
    }
}