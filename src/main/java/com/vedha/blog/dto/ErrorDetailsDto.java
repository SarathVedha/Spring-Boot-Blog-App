package com.vedha.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetailsDto {

    private Date timeStamp;

    private String errorCode;

    private String errorMessage;

    private String details;
}
