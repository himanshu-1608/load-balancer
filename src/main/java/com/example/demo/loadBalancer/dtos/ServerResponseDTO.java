package com.example.demo.loadBalancer.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ServerResponseDTO {

    String response;

    UUID code;

}
