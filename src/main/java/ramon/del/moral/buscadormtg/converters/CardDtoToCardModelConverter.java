package ramon.del.moral.buscadormtg.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.entities.CardModel;

import java.util.ArrayList;

@Component
public class CardDtoToCardModelConverter implements Converter<CardDto, CardModel> {

    @Override
    public CardModel convert(CardDto cardDto) {
        Assert.notNull(cardDto, "Source CardDto must not be null");

        return CardModel.builder()
                        .id(cardDto.getId())
                        .name(cardDto.getName())
                        .types(cardDto.getTypes())
                        .manaCost(cardDto.getManaCost())
                        .oracle(cardDto.getOracle())
                        .imageUrl(cardDto.getImageUrl())
                        .collections(new ArrayList<>())
                        .build();
    }
}