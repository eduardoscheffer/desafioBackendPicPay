package eduardo.picPaySimplificado.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // indica que ela é responsável por tratar exceções globalmente em toda a aplicação.
public class RequestsExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class) // significa que ele trata qualquer exceção do tipo EntityNotFoundException lançada por qualquer controlador da aplicação.
    public ResponseEntity threat404() {
        return ResponseEntity.badRequest().body("Dado não encontrado.");
    }
}
