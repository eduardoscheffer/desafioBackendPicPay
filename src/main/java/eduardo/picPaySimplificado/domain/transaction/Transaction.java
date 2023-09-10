package eduardo.picPaySimplificado.domain.transaction;

import eduardo.picPaySimplificado.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToOne // essa transacao so pode ter um payer, mas esse payer pode ter varias transacoes
    @JoinColumn(name = "payer_id")
    private User payer; // sender

    @ManyToOne // essa transacao so pode ter um recebedor, mas esse recebedor pode receber varias transacoes
    @JoinColumn(name = "payee_id")
    private User payee; // receiver

    private LocalDateTime transactionTime;


}
