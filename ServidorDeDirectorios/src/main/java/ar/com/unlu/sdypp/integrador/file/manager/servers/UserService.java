package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.UserCrud;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.UserCrudRepository;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserCrudRepository userRepository;

    public UserCrud findByUsername(String username) {
        var optional = this.userRepository.findById(username);
        if (!optional.isPresent()) {
            var newUser = new UserCrud();
            newUser.setUsername(username);
            this.userRepository.save(newUser);
            return newUser;
        }
        else {
            return optional.get();
        }

    }
}
