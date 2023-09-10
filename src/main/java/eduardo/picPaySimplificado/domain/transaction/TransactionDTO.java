package eduardo.picPaySimplificado.domain.transaction;

import eduardo.picPaySimplificado.domain.user.User;

import java.math.BigDecimal;

public record TransactionDTO(
    BigDecimal amount,
    Long payerId,
    Long payeeId
) {
}
