package com.sysco.sampleService.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProductEnvelopeResponse<T> {

    private List<T> data;

    private List<ErrorResponse> errorResponseList;

    public static <T> ProductEnvelopeResponse<T> generateResponse(List<T> data, List<ErrorResponse> errorResponseList) {
        return ProductEnvelopeResponse.<T>builder()
                .data(data)
                .errorResponseList(errorResponseList)
                .build();
    }


}
