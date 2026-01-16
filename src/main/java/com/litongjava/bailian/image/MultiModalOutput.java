package com.litongjava.bailian.image;

import java.util.List;

public class MultiModalOutput {
  private List<MultiModalChoice> choices;
  private MultiModalTaskMetric task_metric;

  public MultiModalOutput() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalOutput(List<MultiModalChoice> choices, MultiModalTaskMetric task_metric) {
    super();
    this.choices = choices;
    this.task_metric = task_metric;
  }

  public List<MultiModalChoice> getChoices() {
    return choices;
  }

  public void setChoices(List<MultiModalChoice> choices) {
    this.choices = choices;
  }

  public MultiModalTaskMetric getTask_metric() {
    return task_metric;
  }

  public void setTask_metric(MultiModalTaskMetric task_metric) {
    this.task_metric = task_metric;
  }
}
