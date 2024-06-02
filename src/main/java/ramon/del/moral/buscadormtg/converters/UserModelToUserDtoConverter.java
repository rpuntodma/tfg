package ramon.del.moral.buscadormtg.converters;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.entities.UserModel;

import java.util.stream.Collectors;

@Component
public class UserModelToUserDtoConverter implements Converter<UserModel, UserDto> {

    @Resource
    private CollectionModelToCollectionDtoConverter collectionModelToCollectionDtoConverter;

    @Override
    public UserDto convert(UserModel userModel) {
        return UserDto.builder()
                      .id(userModel.getId())
                      .name(userModel.getName())
                      .password(userModel.getPassword())
                      .collections(userModel.getUserCollections()
                                            .stream()
                                            .map(collectionModelToCollectionDtoConverter::convert)
                                            .collect(Collectors.toList()))
                      .build();
    }
}
