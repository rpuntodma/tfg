package ramon.del.moral.buscadormtg.converters;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.entities.CollectionModel;

import java.util.stream.Collectors;

@Component
public class CollectionModelToCollectionDtoConverter implements Converter<CollectionModel, CollectionDto> {

    @Resource
    CardModelToCardDtoConverter cardModelToCardDtoConverter;

    @Override
    public CollectionDto convert(CollectionModel collectionModel) {
        Assert.notNull(collectionModel, "Source CollectionModel must not be null");

        return CollectionDto.builder()
                            .name(collectionModel.getName())
                            .cards(collectionModel.getCards()
                                                  .stream()
                                                  .map(cardModelToCardDtoConverter::convert)
                                                  .collect(Collectors.toSet()))
                            .build();
    }
}
