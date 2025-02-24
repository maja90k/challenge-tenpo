package cl.tenpo.api_challenge_tenpo.mapper;

import cl.tenpo.api_challenge_tenpo.dto.TransactionDto;
import cl.tenpo.api_challenge_tenpo.model.Transaction;
import cl.tenpo.api_challenge_tenpo.sqlBuilder.TransactionSqlBuilder;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TransactionMapper {

  @Select("select * FROM transaction where id = #{id}")
  @Results({
      @Result(id = true, property = "id", column = "id"),
      @Result(property = "amount", column = "amount"),
      @Result(property = "bankDraft", column = "bank_draft"),
      @Result(property = "name", column = "name"),
      @Result(property = "transactionDate", column = "date"),})
  Transaction findById(int id);

  @SelectProvider(type = TransactionSqlBuilder.class, method = "findAll")
  @Results({
      @Result(id = true, property="id", column="id"),
      @Result(property="amount", column="amount"),
      @Result(property="bankDraft", column="bank_draft"),
      @Result(property="name", column="name"),
      @Result(property="transactionDate", column="date"),})
  public List<Transaction> findAll(Map<String, String> params);

  @Insert("INSERT INTO transaction (amount, bank_draft, name, date)"
      + "VALUES (#{amount}, #{bankDraft}, #{name}, #{transactionDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  public Integer save(Transaction transaction);

  @Select("SELECT COUNT(*) FROM transaction WHERE name = #{name}")
  public Integer countTransactionsByUser(String name);

  @Delete("DELETE FROM transaction WHERE id = #{id}")
  public Integer delete(Integer id);

  @Update("UPDATE transaction SET " +
      "name = #{name}, amount = #{amount}, bank_draft = #{bankDraft} WHERE id = #{id}")
  public Integer update(TransactionDto existingTransaction);
}
