package com.litongjava.gitee;

import java.util.List;

/**
 * 文档中单个页面的解析结果
 */
public class GiteeDocumentPage {

  /**
   * 检测到的区域框列表，每个 bbox 为 [x1, y1, x2, y2]
   */
  private List<List<Integer>> bboxes;

  /**
   * bboxes 数量
   */
  private Integer bbox_count;

  /**
   * 页码索引，从 0 开始
   */
  private Integer page_index;

  /**
   * 当前页解析出的文本（带 ref 标记）
   */
  private String text_result;

  /**
   * 当前页渲染后的结果图片地址
   */
  private String result_image;

  public List<List<Integer>> getBboxes() {
    return bboxes;
  }

  public void setBboxes(List<List<Integer>> bboxes) {
    this.bboxes = bboxes;
  }

  public Integer getBbox_count() {
    return bbox_count;
  }

  public void setBbox_count(Integer bbox_count) {
    this.bbox_count = bbox_count;
  }

  public Integer getPage_index() {
    return page_index;
  }

  public void setPage_index(Integer page_index) {
    this.page_index = page_index;
  }

  public String getText_result() {
    return text_result;
  }

  public void setText_result(String text_result) {
    this.text_result = text_result;
  }

  public String getResult_image() {
    return result_image;
  }

  public void setResult_image(String result_image) {
    this.result_image = result_image;
  }
}
