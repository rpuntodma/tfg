package ramon.del.moral.buscadormtg.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CardDto {

    private String name;
    private String types;
    private String manaCost;
    private String oracle;
    private String imageUrl;
}