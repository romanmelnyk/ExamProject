package sharecare.service;

import sharecare.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> register(User user);

    Optional<User> checkCredentials(String email, String password);

}
