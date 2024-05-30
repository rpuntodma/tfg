package ramon.del.moral.buscadormtg.facades.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.converters.CardModelToCardDtoConverter;
import ramon.del.moral.buscadormtg.converters.CollectionDtoToCollectionModelConverter;
import ramon.del.moral.buscadormtg.converters.CollectionModelToCollectionDtoConverter;
import ramon.del.moral.buscadormtg.daos.CollectionDao;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.facades.CollectionFacade;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DefaultCollectionFacade implements CollectionFacade {

    @Resource
    private CollectionDao collectionService;
    @Resource
    private CollectionDtoToCollectionModelConverter collectionDtoToCollectionModelConverter;
    @Resource
    private CollectionModelToCollectionDtoConverter collectionModelToCollectionDtoConverter;
    @Resource
    private CardModelToCardDtoConverter cardModelToCardDtoConverter;

    @Override
    public List<CollectionDto> findAll() {
        return collectionService.findAll()
                                .stream()
                                .map(collectionModelToCollectionDtoConverter::convert)
                                .collect(Collectors.toList());
    }

    @Override
    public Optional<CollectionDto> findById(Long id) {
        return collectionService.findById(id)
                                .map(collectionModelToCollectionDtoConverter::convert);
    }

    @Override
    public CollectionDto save(CollectionDto collectionDto) {
        return collectionModelToCollectionDtoConverter
                .convert(collectionService
                        .save(Objects.requireNonNull(collectionDtoToCollectionModelConverter
                                .convert(collectionDto))));
    }

    @Override
    public void deleteById(Long id) {
        collectionService.deleteById(id);
    }

    @Override
    public Set<CardDto> findCardsByCollectionId(Long collectionId) {
        return collectionService.findCardsByCollectionId(collectionId)
                                .stream()
                                .map(cardModelToCardDtoConverter::convert)
                                .collect(Collectors.toSet());
    }
}
