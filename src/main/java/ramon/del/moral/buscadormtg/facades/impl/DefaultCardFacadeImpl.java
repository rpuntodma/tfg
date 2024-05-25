package ramon.del.moral.buscadormtg.facades.impl;

import jakarta.annotation.Resource;
import ramon.del.moral.buscadormtg.converters.CardStringToCardDtoConverter;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.facades.CardFacade;
import ramon.del.moral.buscadormtg.services.CardService;
import ramon.del.moral.buscadormtg.services.ScryfallService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DefaultCardFacadeImpl implements CardFacade {

    @Resource
    private CardService cardService;
    @Resource
    private ScryfallService scryfallService;
    @Resource
    private CardStringToCardDtoConverter cardStringToCardDtoConverter;

    @Override
    public List<CardDto> findAll() {
        return List.of();
    }

    @Override
    public Optional<CardDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CardDto save(CardDto card) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<CardDto> searchCardsByName(String name) throws IOException, InterruptedException {

        return cardStringToCardDtoConverter.convert(scryfallService.searchCards(name));
    }
}
