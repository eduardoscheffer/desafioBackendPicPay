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

    // endpoint com login
    @PostMapping("login")
    public ResponseEntity validarSenha(@RequestBody UserDTO userDTO) {
        Boolean valid = service.validarSenha(userDTO);
        if (!valid) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else return new ResponseEntity<>(HttpStatus.OK);
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

    // update user by Id:
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody UserDTO userDto) {
        User user = service.upDateUserById(id, userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // delete User by ID:
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{id}")
    public ResponseEntity deletUserById(@PathVariable Long id) {
        if (service.deleteUserById(id))
            return ResponseEntity.noContent().build();
        else throw new EntityNotFoundException();
    }


}
