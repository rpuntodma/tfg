package ramon.del.moral.buscadormtg.services.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ramon.del.moral.buscadormtg.daos.CardDao;
import ramon.del.moral.buscadormtg.entities.CardModel;
import ramon.del.moral.buscadormtg.services.CardService;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultCardServiceImpl implements CardService {

    @Resource
    private CardDao cardDao;

    @Override
    public List<CardModel> findAll() {
        return cardDao.findAll();
    }

    @Override
    public Optional<CardModel> findById(Long id) {
        return cardDao.findById(id);
    }

    @Override
    public CardModel save(CardModel card) {
        return cardDao.save(card);
    }

    @Override
    public void deleteById(Long id) {
        cardDao.deleteById(id);
    }

    @Override
    public List<CardModel> findCardsByCollectionId(Long id) {
        return cardDao.findCardsByCollectionId(id);
    }


}
