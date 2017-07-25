package com.mjs.statistic.service.server.controller;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mjs.statistic.service.server.business.StatisticBO;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsControllerTest {

  @Mock
  StatisticBO statisticBO;

  @InjectMocks
  StatisticController statisticController = new StatisticController();

  @Test
  public void shouldRetrieveSummary() throws IOException {
    Summary expectedSummary = Summary.emptySummaryBuilder();
    Mockito.when(statisticBO.getSummary()).thenReturn(expectedSummary);
    Summary summary = statisticController.fetchStatistics();

    Assert.assertNotNull(summary);
    assertEquals(expectedSummary, summary);
  }

  @Test
  public void shouldCallInsertWhenMethodIsCalled() throws IOException {
    Transaction transaction = transactionBuilder();

    statisticController.insert(transaction);
    verify(statisticBO, times(1)).insert(transaction);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldPassTheExceptionReceived() throws IOException {
    Transaction transaction = transactionBuilder();
    doThrow(new IllegalArgumentException()).when(statisticBO).insert(transaction);
    statisticController.insert(transaction);
  }

  private Transaction transactionBuilder() {
    return new Transaction(Double.valueOf(0), Long.valueOf(0));
  }
}
