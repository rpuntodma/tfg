package ramon.del.moral.buscadormtg.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class CollectionDto {

    private String name;
    private Set<CardDto> cards;
}
