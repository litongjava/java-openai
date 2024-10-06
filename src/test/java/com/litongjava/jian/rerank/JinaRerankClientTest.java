package com.litongjava.jian.rerank;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class JinaRerankClientTest {

  @Test
  public void test() {
    EnvUtils.set("JINA_API_KEY", "");
    
    List<String> documents = new ArrayList<>();
    documents.add(
        "Bio-Hautpflege für empfindliche Haut mit Aloe Vera und Kamille: Erleben Sie die wohltuende Wirkung unserer Bio-Hautpflege, speziell für empfindliche Haut entwickelt. Mit den beruhigenden Eigenschaften von Aloe Vera und Kamille pflegen und schützen unsere Produkte Ihre Haut auf natürliche Weise. Verabschieden Sie sich von Hautirritationen und genießen Sie einen strahlenden Teint.");
    documents.add(
        "Neue Make-up-Trends setzen auf kräftige Farben und innovative Techniken: Tauchen Sie ein in die Welt der modernen Schönheit mit den neuesten Make-up-Trends. Kräftige, lebendige Farben und innovative Techniken setzen neue Maßstäbe. Von auffälligen Eyelinern bis hin zu holografischen Highlightern – lassen Sie Ihrer Kreativität freien Lauf und setzen Sie jedes Mal ein Statement.");
    documents.add(
        "Cuidado de la piel orgánico para piel sensible con aloe vera y manzanilla: Descubre el poder de la naturaleza con nuestra línea de cuidado de la piel orgánico, diseñada especialmente para pieles sensibles. Enriquecidos con aloe vera y manzanilla, estos productos ofrecen una hidratación y protección suave. Despídete de las irritaciones y saluda a una piel radiante y saludable.");
    documents.add(
        "Las nuevas tendencias de maquillaje se centran en colores vivos y técnicas innovadoras: Entra en el fascinante mundo del maquillaje con las tendencias más actuales. Colores vivos y técnicas innovadoras están revolucionando el arte del maquillaje. Desde delineadores neón hasta iluminadores holográficos, desata tu creatividad y destaca en cada look.");
    documents.add("针对敏感肌专门设计的天然有机护肤产品：体验由芦荟和洋甘菊提取物带来的自然呵护。我们的护肤产品特别为敏感肌设计，温和滋润，保护您的肌肤不受刺激。让您的肌肤告别不适，迎来健康光彩。");
    documents.add("新的化妆趋势注重鲜艳的颜色和创新的技巧：进入化妆艺术的新纪元，本季的化妆趋势以大胆的颜色和创新的技巧为主。无论是霓虹眼线还是全息高光，每一款妆容都能让您脱颖而出，展现独特魅力。");
    documents.add("敏感肌のために特別に設計された天然有機スキンケア製品: アロエベラとカモミールのやさしい力で、自然の抱擁を感じてください。敏感肌用に特別に設計された私たちのスキンケア製品は、肌に優しく栄養を与え、保護します。肌トラブルにさようなら、輝く健康な肌にこんにちは。");
    documents.add("新しいメイクのトレンドは鮮やかな色と革新的な技術に焦点を当てています: 今シーズンのメイクアップトレンドは、大胆な色彩と革新的な技術に注目しています。ネオンアイライナーからホログラフィックハイライターまで、クリエイティビティを解き放ち、毎回ユニークなルックを演出しましょう。");

    RerankReqVo reqVo = new RerankReqVo();
    reqVo.setModel(JinaModel.jina_reranker_v2_base_multilingual);
    reqVo.setQuery("Organic skincare products for sensitive skin");
    reqVo.setTop_n(3);
    reqVo.setDocuments(documents);

    RerankRespVo respVo = JinaRerankClient.rerank(reqVo);
    System.out.println(JsonUtils.toJson(respVo));
  }

}
