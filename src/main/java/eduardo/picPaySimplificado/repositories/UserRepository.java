package eduardo.picPaySimplificado.repositories;

import eduardo.picPaySimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
