package com.mjs.statistic.service.server.dao;

import java.util.List;

import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;


public interface StatisticRepository {

  /**
   * Method reposible to insert a transaction into our repository.
   * @param transaction
   */
  void insert(Transaction transaction);

  /**
   * Method responsible to fetch all transactions with date after a given time(in milliseconds epoch time)
   * @param startTimeStamp
   * @return list of Transaction that has a timestap bigger than its parameter.
   */
  List<Transaction> fetch(Long startTimeStamp);
}
