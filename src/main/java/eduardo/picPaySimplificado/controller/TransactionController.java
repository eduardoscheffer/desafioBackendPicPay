package eduardo.picPaySimplificado.controller;

import eduardo.picPaySimplificado.domain.transaction.Transaction;
import eduardo.picPaySimplificado.domain.transaction.TransactionDTO;
import eduardo.picPaySimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    // create Transaction:
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        System.out.println(transactionDTO);
        var newTransaction = transactionService.createTransaction(transactionDTO);
        System.out.println("NEW TRANSACTION: " + newTransaction);
        System.out.println("finished");
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        var transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
