package com.mjs.statistic.service.server.business;

import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

/**
 * Business object that contains the rules to handle transactions.
 *
 * @author msaciloto
 */
public interface StatisticBO {

  /**
   * this method is responsible for receiving a transaction object
   * validate and insert it into repository.
   *
   * @param transaction
   */
  void insert(Transaction transaction);

  /**
   * this method will retrieve a summary by processing a range of
   * given transactions.
   *
   * @return @Summary
   */
  Summary getSummary();
}
