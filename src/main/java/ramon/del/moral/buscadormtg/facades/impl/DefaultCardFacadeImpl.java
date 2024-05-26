package ramon.del.moral.buscadormtg.facades.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.converters.CardDtoToCardModelConverter;
import ramon.del.moral.buscadormtg.converters.CardModelToCardDtoConverter;
import ramon.del.moral.buscadormtg.converters.CardStringToCardDtoConverter;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.facades.CardFacade;
import ramon.del.moral.buscadormtg.services.CardService;
import ramon.del.moral.buscadormtg.services.ScryfallService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultCardFacadeImpl implements CardFacade {

    @Resource
    private CardService cardService;
    @Resource
    private ScryfallService scryfallService;
    @Resource
    private CardModelToCardDtoConverter cardModelToCardDtoConverter;
    @Resource
    private CardDtoToCardModelConverter cardDtoToCardModelConverter;
    @Resource
    private CardStringToCardDtoConverter cardStringToCardDtoConverter;

    @Override
    public List<CardDto> findAll() {
        return cardService.findAll()
                          .stream()
                          .map(cardModelToCardDtoConverter::convert)
                          .collect(Collectors.toList());
    }

    @Override
    public Optional<CardDto> findById(Long id) {
        return cardService.findById(id)
                          .map(cardModelToCardDtoConverter::convert);
    }

    @Override
    public CardDto save(CardDto cardDto) {
        return cardModelToCardDtoConverter
                .convert(cardService
                        .save(cardDtoToCardModelConverter
                                .convert(cardDto)));
    }

    @Override
    public void deleteById(Long id) {
        cardService.deleteById(id);
    }

    @Override
    public List<CardDto> searchCardsByName(String name) throws IOException, InterruptedException {

        return cardStringToCardDtoConverter.convert(scryfallService.searchCards(name));
    }
}