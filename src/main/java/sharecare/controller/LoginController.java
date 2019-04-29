package sharecare.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharecare.model.dto.Credentials;
import sharecare.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity login(@Valid @RequestBody Credentials credentials) {
        val user = userService.checkCredentials(credentials.getEmail(), credentials.getPassword());
        return user.isPresent() ? ResponseEntity.of(user) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
