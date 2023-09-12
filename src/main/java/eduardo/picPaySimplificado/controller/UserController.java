package eduardo.picPaySimplificado.controller;

import eduardo.picPaySimplificado.domain.user.User;
import eduardo.picPaySimplificado.domain.user.UserDTO;
import eduardo.picPaySimplificado.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    // dependency injection
    @Autowired
    private UserService service;

    // create user:
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = service.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // list of users
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        var users = service.getAllUsers();
        if (users.isEmpty()) {
            throw new EntityNotFoundException(); // dispara a @ExceptionHandler(EntityNotFoundException.class)
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
