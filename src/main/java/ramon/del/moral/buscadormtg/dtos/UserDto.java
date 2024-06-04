package ramon.del.moral.buscadormtg.dtos;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

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
