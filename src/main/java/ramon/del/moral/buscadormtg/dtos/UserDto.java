package ramon.del.moral.buscadormtg.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String password;
}
