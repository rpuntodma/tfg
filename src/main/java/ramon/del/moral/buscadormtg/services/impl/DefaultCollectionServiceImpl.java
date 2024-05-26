package ramon.del.moral.buscadormtg.services.impl;

import jakarta.annotation.Resource;
import ramon.del.moral.buscadormtg.daos.CollectionDao;
import ramon.del.moral.buscadormtg.entities.CollectionModel;
import ramon.del.moral.buscadormtg.services.CollectionService;

import java.util.List;
import java.util.Optional;

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
}
