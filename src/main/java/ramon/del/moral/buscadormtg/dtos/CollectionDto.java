package ramon.del.moral.buscadormtg.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class CollectionDto {

    private Long id;
    @NonNull
    private String name;
    private Set<CardDto> cards;
}
