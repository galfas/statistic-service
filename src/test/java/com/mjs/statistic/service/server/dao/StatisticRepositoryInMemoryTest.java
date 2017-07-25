package com.mjs.statistic.service.server.dao;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.statistic.service.server.dao.impl.StatisticRepositoryInMemory;
import com.mjs.statistic.service.server.model.Transaction;


@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticRepositoryInMemoryTest {

  StatisticRepositoryInMemory statisticRepository = new StatisticRepositoryInMemory();


  @Test
  public void shouldReturnAllBecauseAllareInTheTimeRange() throws IOException {
    long nowInMilli = Instant.now().toEpochMilli();
    long givenLimit = nowInMilli - 300000;

    statisticRepository.insert(transactionBuilder(Double.valueOf(0), nowInMilli));
    statisticRepository.insert(transactionBuilder(Double.valueOf(0), nowInMilli));
    statisticRepository.insert(transactionBuilder(Double.valueOf(0), nowInMilli));
    statisticRepository.insert(transactionBuilder(Double.valueOf(0), nowInMilli));

    List<Transaction> transactionList = statisticRepository.fetch(givenLimit);

    Assert.assertNotNull(transactionList);
    Assert.assertEquals(4, transactionList.size());
  }

  @Test
  public void shouldReturnHalfBecauseHalfAreInTheTimeRange() throws IOException {
    long nowInMilli = Instant.now().toEpochMilli();
    long givenLimit = nowInMilli - 30000;

    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli()));
    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli()));
    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli() - 200000));
    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli() - 200000));


    List<Transaction> transactionList = statisticRepository.fetch(givenLimit);

    Assert.assertNotNull(transactionList);
    Assert.assertEquals(2, transactionList.size());
  }

  @Test
  public void shouldReturnEmptyListBecauseAllAreOutOfTheTimeRange() throws IOException {
    long nowInMilli = Instant.now().toEpochMilli();
    long givenLimit = nowInMilli - 30000;

    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli() - 200000));
    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli() - 200000));
    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli() - 200000));
    statisticRepository.insert(transactionBuilder(Double.valueOf(10), Instant.now().toEpochMilli() - 200000));


    List<Transaction> transactionList = statisticRepository.fetch(givenLimit);

    Assert.assertNotNull(transactionList);
    Assert.assertEquals(0, transactionList.size());
  }

  private Transaction transactionBuilder(Double amount, Long timestamp) {
    return new Transaction(amount, timestamp);
  }
}
