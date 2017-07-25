package com.mjs.statistic.service.server.dao;

import java.util.List;

import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;


public interface StatisticRepository {

  void insert(Transaction transaction);

  List<Transaction> fetch(Long limit);
}
