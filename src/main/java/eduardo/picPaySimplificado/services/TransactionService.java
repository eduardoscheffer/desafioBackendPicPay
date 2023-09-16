package eduardo.picPaySimplificado.services;

import eduardo.picPaySimplificado.domain.transaction.Transaction;
import eduardo.picPaySimplificado.domain.transaction.TransactionDTO;
import eduardo.picPaySimplificado.domain.user.User;
import eduardo.picPaySimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {

        // validações:
        System.out.println("antes de entrar no banco");
        var payer = userService.findUserById(transactionDTO.payerId());
        var payee = userService.findUserById(transactionDTO.payeeId());
        System.out.println("apos consultar o db");

        userService.validateUser(payer, transactionDTO.amount());

        boolean isAutorithed = authorizeTransaction();

        if (!isAutorithed) throw new Exception("Transação não autorizada.");
        // fim das validações.

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.amount());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setTransactionTime(LocalDateTime.now());

        // atualizar os saldos do payer e payee:
        payer.setBalance(payer.getBalance().subtract(transactionDTO.amount()));
        payee.setBalance(payee.getBalance().add(transactionDTO.amount()));

        // atualizando no banco de dados o User payer e o User payee:
        userService.saveUser(payer);
        userService.saveUser(payee);

        // atualizando a transacao no banco de dados:
        repository.save(newTransaction);

        // servico de envio de notificação:
        notificationService.sendNotification(payer, "Transação realizada com sucesso!");
        notificationService.sendNotification(payer, "Transação recebida com sucesso!");

        return newTransaction;

    }

    // batendo e verificando se a transaçao esta autorizada
    public boolean authorizeTransaction() {
        var response = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String message = (String) response.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else {
            return false;
        }
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }
}
