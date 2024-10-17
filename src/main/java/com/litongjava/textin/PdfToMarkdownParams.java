package com.litongjava.textin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PdfToMarkdownParams {
  private String pdfPwd;
  private Integer dpi;
  private Integer pageStart;
  private Integer pageCount;
  private Boolean applyDocumentTree;
  private String markdownDetails;
  private String tableFlavor;
  private String getImage;
  private String parseMode;
}
