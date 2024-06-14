package ramon.del.moral.buscadormtg.facades.impl;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.entities.CollectionModel;
import ramon.del.moral.buscadormtg.facades.CollectionFacade;
import ramon.del.moral.buscadormtg.services.CollectionService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultCollectionFacade implements CollectionFacade {

    @Resource
    private CollectionService collectionService;
    @Resource
    private Converter<CollectionDto, CollectionModel> collectionDtoToCollectionModelConverter;
    @Resource
    private Converter<CollectionModel, CollectionDto> collectionModelToCollectionDtoConverter;

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
}
