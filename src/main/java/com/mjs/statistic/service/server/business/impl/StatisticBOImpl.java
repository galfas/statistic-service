package com.mjs.statistic.service.server.business.impl;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjs.statistic.service.server.business.StatisticBO;
import com.mjs.statistic.service.server.conf.ServerConfiguration;
import com.mjs.statistic.service.server.dao.StatisticRepository;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

import static com.mjs.statistic.service.server.model.Summary.emptySummaryBuilder;

@Component
public class StatisticBOImpl implements StatisticBO {

  @Autowired
  private StatisticRepository statisticRepository;

  @Autowired
  private ServerConfiguration serverConfiguration;

  private Summary summary = emptySummaryBuilder();


  private Timer timer = new Timer();

  /**
   * {@inheritDoc}
   */
  @Override
  public void insert(Transaction transaction) {
    validateTransaction(transaction);

    statisticRepository.insert(transaction);

    handleInsertionEffects(transaction);
  }

  /**
   *{@inheritDoc}
   */
  public Summary getSummary() {
    return summary;
  }

  private void handleInsertionEffects(Transaction transaction) {
    Long nextJobTime = calculateTransactionExpiration(transaction);
    if (nextJobTime > 0) {
      timer.schedule(new StatisticUpdate(), nextJobTime);
    }
    updateSummary();
  }

  /**
   * this method is responsible for orchestrate the summary generation.
   */
  private void updateSummary() {
    Long maxRange = calculateMinAcceptedTimeInMillis();

    List<Transaction> transactionList = statisticRepository.fetch(maxRange);
    summary = generateSummary(transactionList);
  }

  /**
   * from a range of valid transactions this method is responsible to build Summary.
   *
   * @param transactionList List of transactions that are valid for the current scenario.
   * @return current Summary based on the valid transactions.
   */
  private Summary generateSummary(List<Transaction> transactionList) {
    Double min = Double.MAX_VALUE;
    Double max = Double.MIN_VALUE;
    Double total = Double.valueOf(0);

    if (transactionList.isEmpty())
      return emptySummaryBuilder();

    for (Transaction transaction : transactionList) {
      if (transaction.getAmount() < min) {
        min = transaction.getAmount();
      }
      if (transaction.getAmount() > max) {
        max = transaction.getAmount();
      }
      total += transaction.getAmount();
    }

    return new Summary(total, total / transactionList.size(),
      max, min, Long.valueOf(transactionList.size()));
  }

  /**
   * base on our set limit time range, it will give the time it should be expired from our Summary.
   * @param transaction
   * @return time that a given transaction will expire
   */
  private Long calculateTransactionExpiration(Transaction transaction) {
    Long expirationTime = Instant.now().toEpochMilli() - transaction.getTimestamp();
    return serverConfiguration.getMaxRangeTime() - expirationTime;
  }

  private void validateTransaction(Transaction transaction) {
    Long timeFromNowInMillis = Instant.now().toEpochMilli() - transaction.getTimestamp();

    if (timeFromNowInMillis > serverConfiguration.getMaxRangeTime()) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * It will generate the least acceptable time (in millis) for a transaction,
   * in other other the earliest acceptable time.
   *
   * @return least time in millis acceptable for a transaction to be considered valid.
   */
  private Long calculateMinAcceptedTimeInMillis() {
    Long currentTimestamp = Instant.now().toEpochMilli();

    return currentTimestamp - serverConfiguration.getMaxRangeTime();
  }

  /**
   * Schedule class responsible to run the updateSummary in background.
   */
  class StatisticUpdate extends TimerTask {

    @Override
    public void run() {
      updateSummary();
    }
  }
}
