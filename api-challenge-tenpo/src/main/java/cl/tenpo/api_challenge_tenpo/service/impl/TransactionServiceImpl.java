package cl.tenpo.api_challenge_tenpo.service.impl;

import cl.tenpo.api_challenge_tenpo.dto.TransactionDto;
import cl.tenpo.api_challenge_tenpo.exception.CustomNotFoundException;
import cl.tenpo.api_challenge_tenpo.mapper.TransactionMapper;
import cl.tenpo.api_challenge_tenpo.model.Transaction;
import cl.tenpo.api_challenge_tenpo.service.TransactionService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionMapper transactionMapper;

  @Override
  public List<TransactionDto> findAll(Map<String, String> params) {
    List<Transaction> transactions = transactionMapper.findAll(params);

    return transactions.stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public TransactionDto findById(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("El ID no puede ser negativo");
    }
    Transaction entity = transactionMapper.findById(id);
    if (entity == null) {
      throw new CustomNotFoundException("No se encontró la transacción con ID: " + id);
    }

    return toDto(entity);
  }

  @Override
  public TransactionDto save(TransactionDto transaction) {
    int transactionCount = transactionMapper.countTransactionsByUser(transaction.getName());

    if (transactionCount >= 100) {
      throw new IllegalArgumentException("El cliente ha alcanzado el límite máximo de 100 transacciones");
    }

    if (transaction.getAmount() < 0) {
      throw new IllegalArgumentException("El monto de la transacción no puede ser negativo");
    }

    long currentTime = System.currentTimeMillis();
    long transactionTime = transaction.getTransactionDate().getTime();

    if (transactionTime > currentTime) {
      throw new IllegalArgumentException("La fecha de la transacción no puede ser superior a la fecha y hora actual");
    }

    Transaction entity = new Transaction();
    entity.setAmount(transaction.getAmount());
    entity.setBankDraft(transaction.getBankDraft());
    entity.setName(transaction.getName());
    entity.setTransactionDate(new Timestamp(transactionTime));

    transactionMapper.save(entity);

    return transaction;
  }

  @Override
  public TransactionDto edit(TransactionDto updatedTransaction, int id) {
    Transaction existingTransaction = transactionMapper.findById(id);

    if (existingTransaction == null) {
      throw new CustomNotFoundException("No se encuntra la disponible la transacción con ID: " + id);
    }

    if (updatedTransaction.getName() == null || updatedTransaction.getName().isEmpty()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }
    if (updatedTransaction.getAmount() <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor que cero.");
    }

    existingTransaction.setName(updatedTransaction.getName());
    existingTransaction.setAmount(updatedTransaction.getAmount());
    existingTransaction.setBankDraft(updatedTransaction.getBankDraft());

    transactionMapper.update(toDto(existingTransaction));

    return toDto(existingTransaction);
  }

  @Override
  public void delete(int id) {
    Transaction existingTransaction = transactionMapper.findById(id);
    if (existingTransaction == null) {
      throw new CustomNotFoundException("No se encontró la transacción con ID: " + id);
    }
    transactionMapper.delete(id);
  }

  private TransactionDto toDto(Transaction transaction) {
    TransactionDto dto = new TransactionDto();
    dto.setId(transaction.getId());
    dto.setAmount(transaction.getAmount());
    dto.setBankDraft(transaction.getBankDraft());
    dto.setName(transaction.getName());
    dto.setTransactionDate(transaction.getTransactionDate());
    return dto;
  }
}