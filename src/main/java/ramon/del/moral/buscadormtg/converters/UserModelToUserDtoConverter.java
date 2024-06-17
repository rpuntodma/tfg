package ramon.del.moral.buscadormtg.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.entities.UserModel;

@Component
public class UserModelToUserDtoConverter implements Converter<UserModel, UserDto> {

    @Override
    public UserDto convert(UserModel userModel) {
        return UserDto.builder()
                      .id(userModel.getId())
                      .name(userModel.getName())
                      .password(userModel.getPassword())
                      .build();
    }
}