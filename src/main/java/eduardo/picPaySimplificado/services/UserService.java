package eduardo.picPaySimplificado.services;

import eduardo.picPaySimplificado.domain.user.User;
import eduardo.picPaySimplificado.domain.user.UserDTO;
import eduardo.picPaySimplificado.domain.user.UserType;
import eduardo.picPaySimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User newUser) {
        repository.save(newUser);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User findUserById(Long userId) throws Exception {
        return repository.findById(userId).orElseThrow(() -> new Exception("Usuario nao encontrado"));
    }

    public boolean validateUser(User payer, BigDecimal amount) throws Exception {
        if (payer.getUserType() == UserType.MERCHANT) throw new Exception("Usuario lojista não pode realizae transação");
        if (payer.getBalance().compareTo(amount) < 0) throw new Exception("Saldo insuficiente");

        return true;
    }

    @Transactional
    public User upDateUserById(Long id, UserDTO userDto) {
        return repository.findById(id).map((existingUser) -> {
            existingUser.setName(userDto.name());
            existingUser.setDocument(userDto.document());
            existingUser.setEmail(userDto.email());
            existingUser.setPassword(userDto.password());
            existingUser.setUserType(userDto.userType());
            return repository.save(existingUser);
        }).orElse(null);
    }

    @Transactional
    public boolean deleteUserById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        else return false;
    }
}
