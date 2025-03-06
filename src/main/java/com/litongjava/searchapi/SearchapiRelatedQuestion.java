package com.litongjava.searchapi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiRelatedQuestion {
    private String question;
    private String answer;
    private String answer_highlight;
    private List<SearchApiAnswerListItem> answer_list;
    private SearchapiSource source;
}
