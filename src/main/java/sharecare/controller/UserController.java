package sharecare.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sharecare.model.User;
import sharecare.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody User user) {
        val registeredUser = userService.register(user);
        return registeredUser.isPresent()
                ? ResponseEntity.of(registeredUser)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email already exists");
    }
}
