package com.example.demo.loadBalancer.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerDTO {

    private String code;

    private String status;

    private Integer totalConnectionsCount;

}
