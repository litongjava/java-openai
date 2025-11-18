package com.litongjava.gitee;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litongjava.tio.utils.hutool.StrUtil;

/**
 * 工具：将 Gitee 文档解析结果转换为可阅读的 Markdown（包含图片引用 + 页码）
 */
public class GiteeSimpleMarkdownUtils {

  /**
   * 任意 ref + det 整块：
   * <|ref|>xxx<|/ref|><|det|>[[...]]<|/det|>
   * 统一用一个简单的模式整体删除（不保留坐标）
   */
  public static final Pattern ANY_REF_BLOCK_PATTERN = Pattern.compile("(?s)<\\|ref\\|>.*?<\\|/det\\|>");

  /**
   * image 专用块：
   * <|ref|>image<|/ref|><|det|>[[...]]<|/det|>
   * 先替换成 Markdown 图片，再统一删除其他 ref/det
   */
  public static final Pattern IMAGE_REF_BLOCK_PATTERN = Pattern.compile("(?s)<\\|ref\\|>image<\\|/ref\\|><\\|det\\|>.*?<\\|/det\\|>");

  /**
   * 整体结果转 Markdown（带页码）
   */
  public static String toMarkdown(GiteeDocumentOutput output) {
    return toMarkdown(output, null);
  }

  public static String toMarkdown(GiteeDocumentOutput output, Function<String, String> func) {
    if (output == null) {
      return "";
    }
    List<GiteeDocumentPage> pages = output.getPages();
    if (pages == null || pages.isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    int fallbackIndex = 0;
    boolean firstPage = true;

    for (GiteeDocumentPage page : pages) {
      String pageMd = pageToMarkdown(page, func);

      if (!StrUtil.isBlank(pageMd)) {
        int pageIndex = page.getPage_index() != null ? page.getPage_index() : fallbackIndex;
        int pageNumber = pageIndex + 1;

        // 只有第二页及以后加分隔线
        if (!firstPage) {
          sb.append("---\n\n");
        }
        sb.append("> Page ").append(pageNumber).append("\n\n");
        sb.append(pageMd.trim()).append("\n\n");

        firstPage = false;
      }
      fallbackIndex++;
    }
    return sb.toString().trim();
  }

  /**
   * 单页结果转 Markdown（在 image 标记处插入图片引用）
   */
  private static String pageToMarkdown(GiteeDocumentPage page, Function<String, String> func) {
    if (page == null) {
      return "";
    }

    String raw = page.getText_result();
    String imageUrl = page.getResult_image();
    boolean notBlank = StrUtil.isNotBlank(imageUrl);

    if (notBlank && func != null) {
      imageUrl = func.apply(imageUrl);
    }

    // text_result 为 null 或只有 "..." / "...." 等占位时，认为没有可用文本
    if (StrUtil.isBlank(raw) || "....".equals(raw.trim()) || "...".equals(raw.trim())) {
      // 仅有整页截图也可以输出
      if (notBlank) {
        return "![](" + imageUrl + ")";
      }
      return "";
    }

    String text = raw;

    // 1. 用整页图片 URL 替换 image ref 标记
    text = replaceImageRefWithMarkdown(text, imageUrl);

    // 2. 删除所有 ref/det 区块（包括 title/sub_title/text/image/equation 等）
    text = removeAllRefDetBlocks(text);

    // 3. 清理可能残留的标签（防御性处理）
    text = text.replace("<|ref|>", "").replace("<|/ref|>", "").replace("<|det|>", "").replace("<|/det|>", "").trim();

    // 4. 如果页内没有任何图片引用，但有整页截图，可在页尾补一张截图
    if (!StrUtil.isBlank(imageUrl) && !text.contains("![](")) {
      text = text + "\n\n![](" + imageUrl + ")";
    }

    return text;
  }

  /**
   * 用整页图片 URL 替换 image ref 标记
   */
  private static String replaceImageRefWithMarkdown(String raw, String imageUrl) {
    if (StrUtil.isBlank(raw)) {
      return "";
    }

    if (StrUtil.isBlank(imageUrl)) {
      // 没有图片 URL，只是简单移除 image 标记
      return IMAGE_REF_BLOCK_PATTERN.matcher(raw).replaceAll("");
    }

    Matcher matcher = IMAGE_REF_BLOCK_PATTERN.matcher(raw);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      String md = "\n\n![](" + imageUrl + ")\n\n";
      matcher.appendReplacement(sb, Matcher.quoteReplacement(md));
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  /**
   * 删除所有 ref/det 块（包含 title / sub_title / text / image / equation 等）
   * 格式：<|ref|>xxx<|/ref|><|det|>[[...]]<|/det|>
   */
  private static String removeAllRefDetBlocks(String raw) {
    if (StrUtil.isBlank(raw)) {
      return "";
    }
    return ANY_REF_BLOCK_PATTERN.matcher(raw).replaceAll("");
  }
}
