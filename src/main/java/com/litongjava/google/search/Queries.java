package com.litongjava.google.search;

import lombok.Data;
import java.util.List;

@Data
public class Queries {
    private List<Query> request;
    private List<Query> nextPage;
}
