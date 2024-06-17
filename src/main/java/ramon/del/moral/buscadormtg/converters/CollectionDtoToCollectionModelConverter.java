package ramon.del.moral.buscadormtg.converters;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.entities.CollectionModel;

import java.util.stream.Collectors;

@Component
public class CollectionDtoToCollectionModelConverter implements Converter<CollectionDto, CollectionModel> {

    @Resource
    private CardDtoToCardModelConverter cardDtoToCardModelConverter;

    @Resource
    private UserDtoToUserModelConverter userDtoToUserModelConverter;

    @Override
    public CollectionModel convert(CollectionDto collectionDto) {
        Assert.notNull(collectionDto, "Source CollectionDto must not be null");

        return CollectionModel.builder()
                              .id(collectionDto.getId())
                              .name(collectionDto.getName())
                              .cards(collectionDto.getCards()
                                                  .stream()
                                                  .map(cardDtoToCardModelConverter::convert)
                                                  .collect(Collectors.toSet()))
                              .user(userDtoToUserModelConverter.convert(collectionDto.getUser()))
                              .build();
    }
}