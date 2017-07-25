package com.mjs.statistic.service.server.business;

import java.time.Instant;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.statistic.service.server.business.impl.StatisticBOimpl;
import com.mjs.statistic.service.server.dao.StatisticRepository;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticBOimplTest {

  @Mock
  private StatisticRepository statisticRepository;

  @InjectMocks
  StatisticBO statisticBO = new StatisticBOimpl();

  @Test
  public void shouldInsertTransactionIntoRepository() {
    Long timestamp = Instant.now().toEpochMilli();
    Transaction transaction = transactionBuilder(Double.valueOf(0), timestamp);

    statisticBO.insert(transaction);
    verify(statisticRepository, times(1)).insert(transaction);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotInsertTransactionWithDateOutOfRangeIntoRepository() {
    Long timestamp = 1000941770906L;
    Transaction transaction = transactionBuilder(Double.valueOf(0), timestamp);

    statisticBO.insert(transaction);
    verify(statisticRepository, times(0)).insert(transaction);
  }

  private Transaction transactionBuilder(Double amount, Long timestamp) {
    return new Transaction(amount, timestamp);
  }

  @Test
  public void shouldGenerateSummaryWithAllTransactionInRange() {
    Long timestamp = Instant.now().toEpochMilli();

    Transaction transaction = transactionBuilder(Double.valueOf(10), timestamp);
    Transaction transaction2 = transactionBuilder(Double.valueOf(10), timestamp - 1000);
    Transaction transaction3 = transactionBuilder(Double.valueOf(5), timestamp - 2000);
    Transaction transaction4 = transactionBuilder(Double.valueOf(15), timestamp - 10000);

    Summary expectedSummary =
      new Summary(Double.valueOf(40), Double.valueOf(10), Double.valueOf(15), Double.valueOf(5), Long.valueOf(4));

    when(statisticRepository.fetch(anyLong()))
      .thenReturn(Arrays.asList(transaction, transaction2, transaction3, transaction4));

    Summary currentSummary = statisticBO.fetchSummary();

    assertSummary(expectedSummary, currentSummary);
  }

  @Test
  public void shouldGenerateSummaryWithOneTransactionOutOfRange() {
    Long timestamp = Instant.now().toEpochMilli();

    Transaction transaction = transactionBuilder(Double.valueOf(10), timestamp);

    Summary expectedSummary =
      new Summary(Double.valueOf(10), Double.valueOf(10), Double.valueOf(10), Double.valueOf(10), Long.valueOf(1));

    when(statisticRepository.fetch(anyLong()))
      .thenReturn(Arrays.asList(transaction));

    Summary currentSummary = statisticBO.fetchSummary();

    assertSummary(expectedSummary, currentSummary);
  }

  @Test
  public void shouldGenerateSummaryWithAllTransactionOutOfRange() {
    Long timestamp = Instant.now().toEpochMilli();

    Summary expectedSummary =
      new Summary(Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), Long.valueOf(0));

    when(statisticRepository.fetch(anyLong()))
      .thenReturn(Arrays.asList());

    Summary currentSummary = statisticBO.fetchSummary();

    assertSummary(expectedSummary, currentSummary);
  }

  public void assertSummary(Summary expectedSummary, Summary resultSummary) {

    assertEquals(expectedSummary.getSum(), resultSummary.getSum());
    assertEquals(expectedSummary.getMin(), resultSummary.getMin());
    assertEquals(expectedSummary.getMax(), resultSummary.getMax());
    assertEquals(expectedSummary.getAvg(), resultSummary.getAvg());
  }
}
