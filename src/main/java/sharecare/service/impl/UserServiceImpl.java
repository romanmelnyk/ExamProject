package sharecare.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharecare.model.User;
import sharecare.repository.UserRepository;
import sharecare.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> register(User user) {
        val existingUser = userRepository.findByEmail(user.getEmail());

        return existingUser.isPresent() ? Optional.empty() : Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> checkCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
