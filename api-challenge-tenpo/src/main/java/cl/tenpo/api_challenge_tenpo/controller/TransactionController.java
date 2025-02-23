package cl.tenpo.api_challenge_tenpo.controller;

import cl.tenpo.api_challenge_tenpo.dto.TransactionDto;
import cl.tenpo.api_challenge_tenpo.service.TransactionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    public TransactionService transactionService;

    @CrossOrigin(origins = "*")
    @GetMapping("/transactions/paginated")
    @ResponseBody
    public List<TransactionDto> getAllTransactions(@RequestParam Map<String, String> params) {
        return transactionService.findAll(params);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/transactions/{id}")
    public TransactionDto getAllTransactionsById(@PathVariable("id") int id) {
        return transactionService.findById(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/transaction")
    public TransactionDto saveTransaction(@RequestBody TransactionDto transaction ) {
        return transactionService.save(transaction);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/transaction/{id}")
    public TransactionDto updateTransaction(@RequestBody TransactionDto transaction,@PathVariable("id") int id) {
        return transactionService.edit(transaction, id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/transaction/{id}")
    public void deleteTransaction(@PathVariable("id") int id ) {
        transactionService.delete(id);
    }
}
