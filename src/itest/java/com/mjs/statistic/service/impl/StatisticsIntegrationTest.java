package com.mjs.statistic.service.impl;


import java.time.Instant;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.mjs.statistic.service.AbstractIntegrationTest;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticsIntegrationTest extends AbstractIntegrationTest {

  @Test
  public void shouldRetrieveSummaryBasedOnTwoTransactions() throws Exception {
    Summary expectedSummary =
      new Summary(Double.valueOf(140), Double.valueOf(70), Double.valueOf(100), Double.valueOf(40), Long.valueOf(2));

    Transaction transaction = new Transaction(Double.valueOf(100), Instant.now().toEpochMilli());

    Transaction transaction2 = new Transaction(Double.valueOf(40), Instant.now().toEpochMilli());

    mockMvc.perform(post(
      "/transactions")
      .content(mapper.writeValueAsString(transaction))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated()).andReturn();

    mockMvc.perform(post(
      "/transactions")
      .content(mapper.writeValueAsString(transaction2))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated()).andReturn();

    MvcResult mvcResult = mockMvc.perform(get(
      "/statistics")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk()).andReturn();


    Map summaryAsMap = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Map.class);
    assertSummaryAsMap(expectedSummary, summaryAsMap);
  }

  @Test
  public void shouldRetrieveEmptySummary() throws Exception {
    Summary expectedSummary = Summary.emptySummaryBuilder();

    MvcResult mvcResult = mockMvc.perform(get(
      "/statistics")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk()).andReturn();

    Map summaryAsMap = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Map.class);
    assertSummaryAsMap(expectedSummary, summaryAsMap);
  }

  private void assertSummaryAsMap(Summary expectedSummary, Map summaryAsMap) {
    Assert.assertEquals(expectedSummary.getSum(), summaryAsMap.get("sum"));
    Assert.assertEquals(expectedSummary.getAvg(), summaryAsMap.get("avg"));
    Assert.assertEquals(expectedSummary.getMax(), summaryAsMap.get("max"));
    Assert.assertEquals(expectedSummary.getMin(), summaryAsMap.get("min"));
    Assert.assertEquals(expectedSummary.getCount(), Long.valueOf((Integer) summaryAsMap.get("count")));
  }
}
