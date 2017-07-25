package com.mjs.statistic.service.server.business.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mjs.statistic.service.server.business.StatisticBO;
import com.mjs.statistic.service.server.dao.StatisticRepository;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

@Component
public class StatisticBOimpl implements StatisticBO {

  @Value("${statistics.expiration.time_in_mili}")
  private int MAX_RANGE_TIME;

  @Autowired
  private StatisticRepository statisticRepository;

  @Override
  public void insert(Transaction transaction) {
    Long timeFromNowInMillis = Instant.now().toEpochMilli() - transaction.getTimestamp();

    if (timeFromNowInMillis <= MAX_RANGE_TIME) {
      statisticRepository.insert(transaction);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Summary fetchSummary() {
    Long currentTimestamp = Instant.now().toEpochMilli();
    Long limit = Instant.now().toEpochMilli() - MAX_RANGE_TIME;

    List<Transaction> transactionList = statisticRepository.fetch(limit);

    return generateSummary(transactionList);
  }

  private Summary generateSummary(List<Transaction> validTransactionList) {
    Double min = Double.MAX_VALUE;
    Double max = Double.MIN_VALUE;
    Double total = Double.valueOf(0);

    if (validTransactionList.isEmpty())
      return new Summary(Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), Long.valueOf(0));

    for (Transaction validTransaction : validTransactionList) {
      if (validTransaction.getAmount() < min) {
        min = validTransaction.getAmount();
      }
      if (validTransaction.getAmount() > max) {
        max = validTransaction.getAmount();
      }
      total += validTransaction.getAmount();
    }

    return new Summary(total, total / validTransactionList.size(),
      max, min, Long.valueOf(validTransactionList.size()));
  }
}
