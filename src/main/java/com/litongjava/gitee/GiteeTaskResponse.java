package com.litongjava.gitee;

/**
   * 对应接口返回的任务结构
   */
public class GiteeTaskResponse {
  private String task_id;
  private GiteeDocumentOutput output;
  private String status;
  private Long created_at;
  private Long started_at;
  private Long completed_at;
  private GiteeTaskUrls urls;

  public String getTask_id() {
    return task_id;
  }

  public void setTask_id(String task_id) {
    this.task_id = task_id;
  }

  public GiteeDocumentOutput getOutput() {
    return output;
  }

  public void setOutput(GiteeDocumentOutput output) {
    this.output = output;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Long created_at) {
    this.created_at = created_at;
  }

  public Long getStarted_at() {
    return started_at;
  }

  public void setStarted_at(Long started_at) {
    this.started_at = started_at;
  }

  public Long getCompleted_at() {
    return completed_at;
  }

  public void setCompleted_at(Long completed_at) {
    this.completed_at = completed_at;
  }

  public GiteeTaskUrls getUrls() {
    return urls;
  }

  public void setUrls(GiteeTaskUrls urls) {
    this.urls = urls;
  }
}
