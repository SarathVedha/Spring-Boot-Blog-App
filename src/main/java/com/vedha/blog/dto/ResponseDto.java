package com.vedha.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {

    private List<PostDto> content;

    private int pageNo;

    private int pageSize;

    private int totalPages;

    private Long totalRecords;

    private boolean isLast;
}
