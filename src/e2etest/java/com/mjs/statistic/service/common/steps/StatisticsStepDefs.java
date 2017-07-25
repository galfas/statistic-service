package com.mjs.statistic.service.common.steps;

import java.time.Instant;
import java.util.Map;

import com.jayway.restassured.response.Response;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.mjs.statistic.service.common.BaseApiClient.givenApiClient;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class StatisticsStepDefs {

  Response response;
  Summary summary;

  @Before
  public void setup() throws InterruptedException {
    Thread.sleep(1000);
  }

  @When("^I request the summary$")
  public void i_request_the_summary() {
    response = null;
    response = givenApiClient()
      .get("/statistics");

    assertThat(response.statusCode(), equalTo(200));
    Map summaryAsMap = response.body().as(Map.class);

    summary = buildSummaryFrom(summaryAsMap);
  }

  @When("^I wait \"([^\"]*)\" millis to  request the summary$")
  public void i_request_the_summary_with_a_delay_of(Integer amount) throws InterruptedException {
    Thread.sleep(amount);
    i_request_the_summary();
  }

  @Given("^I register new transaction with amount \"([^\"]*)\"$")
  public void i_register_new_transaction_with_amount(Double amount) {
    i_register_transaction_with_amount_millis_ago(amount, 0L);
  }

  @Given("^I register a transaction with amount \"([^\"]*)\" from \"([^\"]*)\" millis ago$")
  public void i_register_transaction_with_amount_millis_ago(Double amount, Long decrease) {
    Long timestamp = Instant.now().toEpochMilli() - decrease;
    response = null;
    response = givenApiClient()
      .body(new Transaction(amount, timestamp))
      .post("/transactions");

    assertThat(response.statusCode(), equalTo(201));
  }

  @Given("^I register a transaction from last day$")
  public void i_register_a_transaction_fron_last_day() {
    Long timestamp = Instant.now().toEpochMilli() - 86400000;
    response = null;
    response = givenApiClient()
      .body(new Transaction(Double.valueOf(0), timestamp))
      .post("/transactions");
  }

  @Then("^I should see an error$")
  public void i_should_see_an_error() {
    assertThat(response.getStatusCode(), equalTo(204));
  }


  @Then("^I Should see a summary with a max of \"([^\"]*)\" and a min of \"([^\"]*)\"$")
  public void i_should_see_a_summary_with_max_of_and_min_of(Double max, Double min) {
    assertEquals(max, summary.getMax());
    assertEquals(min, summary.getMin());
  }

  @And("^with an average of \"([^\"]*)\"$")
  public void with_an_average_of_(Double avg) {
    assertEquals(avg, summary.getAvg());
  }

  @And("^with a count of \"([^\"]*)\"")
  public void with_a_count_of(Long count) {
    assertEquals(count, summary.getCount());
  }

  @And("^with a sum of \"([^\"]*)\"$")
  public void with_a_sum_of(Double sum) {
    assertEquals(sum, summary.getSum());
  }

  private Summary buildSummaryFrom(Map<String, Object> summaryAsMap) {
    return new Summary((Double) summaryAsMap.get("sum"), (Double) summaryAsMap.get("avg"),
      (Double) summaryAsMap.get("max"), (Double) summaryAsMap.get("min"),
      Long.valueOf((Integer) summaryAsMap.get("count")));
  }
}
