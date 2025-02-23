package cl.tenpo.api_challenge_tenpo.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;

public class TransactionSqlBuilder {

  public String findAll() {
    return new SQL() {{
      SELECT("*");
      FROM("transaction");
    }}.toString();
  }
}