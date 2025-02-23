package cl.tenpo.api_challenge_tenpo.service;

import cl.tenpo.api_challenge_tenpo.dto.TransactionDto;
import cl.tenpo.api_challenge_tenpo.model.Transaction;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface TransactionService {

  List<TransactionDto> findAll(Map<String, String> params);

  TransactionDto findById(int ind);

  TransactionDto save(TransactionDto transaction);

  void delete(int id);

  TransactionDto edit(TransactionDto updatedTransaction, int id);
}
