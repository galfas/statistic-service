package com.mjs.statistic.service.server.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mjs.statistic.service.server.business.StatisticBO;
import com.mjs.statistic.service.server.model.Summary;
import com.mjs.statistic.service.server.model.Transaction;

@RestController
public class StatisticController extends BaseController {

  @Autowired
  StatisticBO statisticBO;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(path = "/transactions", method = RequestMethod.POST)
  public void insert(@RequestBody Transaction transaction) throws IOException {
    statisticBO.insert(transaction);
  }


  @RequestMapping(path = "/statistics")
  @ResponseStatus(HttpStatus.OK)
  public Summary fetchStatistics() throws IOException {
    return statisticBO.fetchSummary();
  }
}
