package cl.tenpo.api_challenge_tenpo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.tenpo.api_challenge_tenpo.dto.TransactionDto;
import cl.tenpo.api_challenge_tenpo.exception.CustomNotFoundException;
import cl.tenpo.api_challenge_tenpo.mapper.TransactionMapper;
import cl.tenpo.api_challenge_tenpo.model.Transaction;
import cl.tenpo.api_challenge_tenpo.service.TransactionService;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionServiceImplTest {

  @Mock
  private TransactionMapper transactionMapper;

  @Mock
  private TransactionService transactionService;


  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFindAllSuccess() {
    Transaction transaction1 = new Transaction();
    transaction1.setId(1);
    transaction1.setName("Cliente1");
    transaction1.setAmount(10000000);
    transaction1.setBankDraft("Banco1");

    Transaction transaction2 = new Transaction();
    transaction2.setId(2);
    transaction2.setName("Cliente2");
    transaction2.setAmount(2009990);
    transaction2.setBankDraft("Banco2");

    when(transactionMapper.findAll(any(Map.class))).thenReturn(
        Arrays.asList(transaction1, transaction2));

    List<TransactionDto> result = transactionService.findAll(new HashMap<>());

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Cliente1", result.get(0).getName());
    assertEquals("Cliente2", result.get(1).getName());
  }

  @Test
  public void testFindByIdSuccess() {
    Transaction transaction = new Transaction();
    transaction.setId(1);
    transaction.setName("Cliente1");
    transaction.setAmount(1008880);
    transaction.setBankDraft("Banco1");

    when(transactionMapper.findById(1)).thenReturn(transaction);
    TransactionDto result = transactionService.findById(1);

    assertNotNull(result);
    assertEquals("Cliente1", result.getName());
  }

  @Test
  public void testFindByIdNegativeId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      transactionService.findById(-1);
    });

    assertEquals("El ID no puede ser negativo", exception.getMessage());
  }

  @Test
  public void testFindByIdNotFound() {
    when(transactionMapper.findById(1)).thenReturn(null);

    Exception exception = assertThrows(CustomNotFoundException.class, () -> {
      transactionService.findById(1);
    });

    assertEquals("No se encontró la transacción con ID: 1", exception.getMessage());
  }

  @Test
  public void testSaveTransactionSuccess() {
    TransactionDto transaction = new TransactionDto();
    transaction.setName("Cliente1");
    transaction.setAmount(100);
    transaction.setBankDraft("Banco1");
    transaction.setTransactionDate(new Timestamp(System.currentTimeMillis() - 1000));

    when(transactionMapper.countTransactionsByUser(transaction.getName())).thenReturn(50);

    TransactionDto result = transactionService.save(transaction);

    assertNotNull(result);
    assertEquals(transaction.getAmount(), result.getAmount());
    verify(transactionMapper).save(any(Transaction.class));
  }

  @Test
  public void testSaveTransactionExceedsTransactionLimit() {
    TransactionDto transaction = new TransactionDto();
    transaction.setName("tenpista");
    transaction.setAmount(100);
    transaction.setBankDraft("Banco1");
    transaction.setTransactionDate(new Timestamp(System.currentTimeMillis() - 1000));

    when(transactionMapper.countTransactionsByUser(transaction.getName())).thenReturn(100);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      transactionService.save(transaction);
    });

    assertEquals("El cliente ha alcanzado el límite máximo de 100 transacciones",
        exception.getMessage());
  }

  @Test
  public void testSaveTransactionNegativeAmount() {
    TransactionDto transaction = new TransactionDto();
    transaction.setName("tenpista");
    transaction.setAmount(-50000);
    transaction.setBankDraft("Banco1");
    transaction.setTransactionDate(new Timestamp(System.currentTimeMillis() - 1000));

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      transactionService.save(transaction);
    });

    assertEquals("El monto de la transacción no puede ser negativo", exception.getMessage());
  }

  @Test
  public void testSaveTransactionFutureTransactionDate() {
    TransactionDto transaction = new TransactionDto();
    transaction.setName("tenpista");
    transaction.setAmount(100000);
    transaction.setBankDraft("Banco1");
    transaction.setTransactionDate(new Timestamp(System.currentTimeMillis() + 10000));

    when(transactionMapper.countTransactionsByUser(transaction.getName())).thenReturn(50);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      transactionService.save(transaction);
    });

    assertEquals("La fecha de la transacción no puede ser superior a la fecha y hora actual",
        exception.getMessage());
  }

  @Test
  public void testDeleteSuccess() {
    int id = 1;

    Transaction existingTransaction = new Transaction();
    existingTransaction.setId(id);

    when(transactionMapper.findById(id)).thenReturn(existingTransaction);
    transactionService.delete(id);
    verify(transactionMapper).delete(id);
  }

  @Test
  public void testDeleteNotFound() {
    int id = 1;

    when(transactionMapper.findById(id)).thenReturn(null);

    Exception exception = assertThrows(CustomNotFoundException.class, () -> {
      transactionService.delete(id);
    });
    assertEquals("No se encontró la transacción con ID: " + id, exception.getMessage());
  }

  @Test
  public void testEditSuccess() {
    int id = 1;
    Transaction existingTransaction = new Transaction();
    existingTransaction.setId(id);
    existingTransaction.setName("Cliente1");
    existingTransaction.setAmount(1006660);
    existingTransaction.setBankDraft("Banco1");

    TransactionDto updatedTransaction = new TransactionDto();
    updatedTransaction.setName("Cliente1 Updated");
    updatedTransaction.setAmount(15060);
    updatedTransaction.setBankDraft("Banco1");

    when(transactionMapper.findById(id)).thenReturn(existingTransaction);
    TransactionDto result = transactionService.edit(updatedTransaction, id);

    assertNotNull(result);
    assertEquals("Cliente1 Updated", result.getName());
    assertEquals(150.0, result.getAmount());

    verify(transactionMapper).update(any(TransactionDto.class));
  }

  @Test
  public void testEditNotFound() {
    int id = 1;
    TransactionDto updatedTransaction = new TransactionDto();
    updatedTransaction.setName("Cliente1");
    updatedTransaction.setAmount(10055555);

    when(transactionMapper.findById(id)).thenReturn(null);
    Exception exception = assertThrows(CustomNotFoundException.class, () -> {
      transactionService.edit(updatedTransaction, id);
    });

    assertEquals("No se encuentra la disponible la transacción con ID: " + id, exception.getMessage());
  }

  @Test
  public void testEditEmptyName() {
    int id = 1;
    Transaction existingTransaction = new Transaction();
    existingTransaction.setId(id);
    existingTransaction.setName("Cliente1");
    existingTransaction.setAmount(100555550);

    TransactionDto updatedTransaction = new TransactionDto();
    updatedTransaction.setName("");
    updatedTransaction.setAmount(100555550);

    when(transactionMapper.findById(id)).thenReturn(existingTransaction);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      transactionService.edit(updatedTransaction, id);
    });

    assertEquals("El nombre no puede estar vacío.", exception.getMessage());
  }

  @Test
  public void testEdit_NegativeAmount() {
    int id = 1;

    Transaction existingTransaction = new Transaction();
    existingTransaction.setId(id);
    existingTransaction.setName("Cliente1");
    existingTransaction.setAmount(10000);

    TransactionDto updatedTransaction = new TransactionDto();
    updatedTransaction.setName("Cliente1 Actualizado");
    updatedTransaction.setAmount(0);

    when(transactionMapper.findById(id)).thenReturn(existingTransaction);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      transactionService.edit(updatedTransaction, id);
    });

    assertEquals("El monto debe ser mayor que cero.", exception.getMessage());
  }
}

