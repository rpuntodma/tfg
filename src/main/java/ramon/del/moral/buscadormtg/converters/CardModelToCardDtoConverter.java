package ramon.del.moral.buscadormtg.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.entities.CardModel;

@Component
public class CardModelToCardDtoConverter implements Converter<CardModel, CardDto> {

    @Override
    public CardDto convert(CardModel cardModel) {
        Assert.notNull(cardModel, "Source cardModel  must not be null");

        return CardDto.builder()
                      .id(cardModel.getId())
                      .name(cardModel.getName())
                      .types(cardModel.getTypes())
                      .manaCost(cardModel.getManaCost())
                      .oracle(cardModel.getOracle())
                      .imageUrl(cardModel.getImageUrl())
                      .build();
    }
}
