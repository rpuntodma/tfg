package ramon.del.moral.buscadormtg.converters;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.entities.UserModel;

import java.util.stream.Collectors;

@Component
public class UserDtoToUserModelConverter implements Converter<UserDto, UserModel> {

    @Override
    public UserModel convert(UserDto userDto) {
        return UserModel.builder()
                      .id(userDto.getId())
                      .name(userDto.getName())
                      .password(userDto.getPassword())
                      .build();
    }
}
