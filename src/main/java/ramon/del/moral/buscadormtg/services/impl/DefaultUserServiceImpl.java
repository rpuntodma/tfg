package ramon.del.moral.buscadormtg.services.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ramon.del.moral.buscadormtg.daos.UserDao;
import ramon.del.moral.buscadormtg.entities.UserModel;
import ramon.del.moral.buscadormtg.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<UserModel> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userDao.save(userModel);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
