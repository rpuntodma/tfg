package ramon.del.moral.buscadormtg.facades.impl;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.entities.UserModel;
import ramon.del.moral.buscadormtg.facades.UserFacade;
import ramon.del.moral.buscadormtg.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultUserFacadeImpl implements UserFacade {

    @Resource
    private UserService userService;
    @Resource
    private Converter<UserModel, UserDto> userModelToUserDtoConverter;
    @Resource
    private Converter<UserDto, UserModel> userDtoToUserModelConverter;

    @Override
    public List<UserDto> findAll() {
        return userService.findAll()
                          .stream()
                          .map(userModelToUserDtoConverter::convert)
                          .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userService.findById(id)
                          .map(userModelToUserDtoConverter::convert);
    }

    @Override
    public UserDto save(UserDto userDto) {
        return userModelToUserDtoConverter
                .convert(userService
                        .save(userDtoToUserModelConverter
                                .convert(userDto)));
    }

    @Override
    public void deleteById(Long id) {
        userService.deleteById(id);
    }
}