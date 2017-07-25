package com.mjs.statistic.service.server.model;


public class Summary {
  private Double sum;
  private Double avg;
  private Double max;
  private Double min;
  private Long count;


  public Summary(Double sum, Double avg, Double max, Double min, Long count) {
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }


  public Double getSum() {
    return sum;
  }

  public Double getAvg() {
    return avg;
  }

  public Double getMax() {
    return max;
  }

  public Double getMin() {
    return min;
  }

  public Long getCount() {
    return count;
  }

  public static Summary emptySummaryBuilder(){
    return new Summary(Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), 0L);
  }
}
