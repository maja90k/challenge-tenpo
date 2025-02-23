package cl.tenpo.api_challenge_tenpo.dto;

import java.util.Date;

public class TransactionDto {

  private int id;
  private int amount;
  private String bankDraft;
  private String name;
  private Date transactionDate;

  public TransactionDto() {
  }
  public TransactionDto(int id, int amount, String bankDraft, String name, Date transactionDate) {
    this.id = id;
    this.amount = amount;
    this.bankDraft = bankDraft;
    this.name = name;
    this.transactionDate = transactionDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getBankDraft() {
    return bankDraft;
  }

  public void setBankDraft(String bankDraft) {
    this.bankDraft = bankDraft;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

}
