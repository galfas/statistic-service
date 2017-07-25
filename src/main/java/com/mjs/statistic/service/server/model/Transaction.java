package com.mjs.statistic.service.server.model;

public class Transaction {
  private Double amount;
  private Long timestamp;

  public Transaction() {
  }

  public Transaction(Double amount, Long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }


  public Double getAmount() {
    return amount;
  }

  public Long getTimestamp() {
    return timestamp;
  }
}
