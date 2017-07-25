package com.mjs.statistic.service.server.business;

import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

public interface StatisticBO {

  void insert(Transaction transaction);

  Summary getSummary();
}
