Feature: statistics retrieval

  Background:
    Given app has started
    And app is health

  Scenario: Return empty Summary without any transaction add
    When I request the summary
    Then I Should see a summary with a max of "0" and a min of "0"
    And with an average of "0"
    And with a count of "0"
    And with a sum of "0"

  Scenario: Return Summary of two transactions
    Given I register new transaction with amount "7000.40"
    And I register new transaction with amount "10200"
    When I request the summary
    Then I Should see a summary with a max of "10200" and a min of "7000.40"
    And with an average of "8600.20"
    And with a count of "2"
    And with a sum of "17200.40"

  Scenario: Return one Summary after one transaction expire
    Given I register a transaction with amount "7000.40" from "500" millis ago
    And I register new transaction with amount "10200"
    When I request the summary with a delay of "500"
    Then I Should see a summary with a max of "10200" and a min of "10200"
    And with an average of "10200"
    And with a count of "1"
    And with a sum of "10200"

  Scenario: I should see an error when I register a legacy transaction
    Given I register a transaction from last day
    Then I should see an error
