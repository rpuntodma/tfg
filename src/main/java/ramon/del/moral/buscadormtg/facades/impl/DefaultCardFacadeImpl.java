package ramon.del.moral.buscadormtg.facades.impl;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.entities.CardModel;
import ramon.del.moral.buscadormtg.entities.CollectionModel;
import ramon.del.moral.buscadormtg.facades.CardFacade;
import ramon.del.moral.buscadormtg.services.CardService;
import ramon.del.moral.buscadormtg.services.CollectionService;
import ramon.del.moral.buscadormtg.services.ScryfallService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DefaultCardFacadeImpl implements CardFacade {

    @Resource
    private CardService cardService;
    @Resource
    private ScryfallService scryfallService;
    @Resource
    private CollectionService collectionService;
    @Resource
    private Converter<CardModel, CardDto> cardModelToCardDtoConverter;
    @Resource
    private Converter<CardDto, CardModel> cardDtoToCardModelConverter;
    @Resource
    private Converter<String, List<CardDto>> cardStringToCardDtoConverter;
    @Resource
    private Converter<CollectionDto, CollectionModel> collectionDtoToCollectionModelConverter;

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
    public CardDto save(CardDto cardDto, CollectionDto collectionDto) {
        CardModel cardModel = cardDtoToCardModelConverter.convert(cardDto);
        Objects.requireNonNull(cardModel)
               .getCollections()
               .add(collectionDtoToCollectionModelConverter.convert(collectionDto));

        return cardModelToCardDtoConverter.convert(cardService.save(cardModel));
    }

    @Override
    public void deleteById(Long id) {
        cardService.deleteById(id);
    }

    @Override
    public List<CardDto> searchCardsByName(String name) throws IOException, InterruptedException {
        return cardStringToCardDtoConverter.convert(scryfallService.searchCards(name.replaceAll(" +", "%20")));
    }

    @Override
    public Set<CardDto> findAllByCollection(Long collectionId) {
        return collectionService.findCardsByCollectionId(collectionId)
                                .stream()
                                .map(cardModelToCardDtoConverter::convert)
                                .collect(Collectors.toSet());
    }
}