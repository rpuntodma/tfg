package ramon.del.moral.buscadormtg.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = { "name", "types", "manaCost" })
@Builder
@AllArgsConstructor
public class CardDto {

    private Long id;
    private String name;
    private String types;
    private String manaCost;
    private String oracle;
    private String imageUrl;

    @Override
    public String toString() {
        return name;
    }
}