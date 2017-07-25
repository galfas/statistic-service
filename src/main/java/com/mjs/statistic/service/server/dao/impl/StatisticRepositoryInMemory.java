package com.mjs.statistic.service.server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mjs.statistic.service.server.dao.StatisticRepository;
import com.mjs.statistic.service.server.model.Transaction;

@Component
public class StatisticRepositoryInMemory implements StatisticRepository {

  private List<Transaction> transactions = new ArrayList<>();

  public void insert(Transaction transaction) {
    transactions.add(transaction);
  }

  public List<Transaction> fetch(Long limit) {
    updateTransactions(limit);

    return transactions;
  }

  private void updateTransactions(Long limit) {
    transactions = transactions.stream()
      .filter(transaction -> transaction.getTimestamp() >= limit)
      .collect(Collectors.toList());
  }
}
