package com.pokeman.api.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class CollectionFormatter<T> {
    private List<T> data;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}