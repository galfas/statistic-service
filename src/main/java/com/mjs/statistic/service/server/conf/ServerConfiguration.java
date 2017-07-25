package com.mjs.statistic.service.server.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerConfiguration {

  @Value("${statistics.expiration.time_in_milli}")
  private int maxRangeTime;

  public int getMaxRangeTime() {
    return maxRangeTime;
  }
}
