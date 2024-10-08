package com.example.demo.loadBalancer.services;

import com.example.demo.loadBalancer.dtos.ServerResponseDTO;
import com.example.demo.loadBalancer.entities.Server;
import com.example.demo.loadBalancer.enums.LoadBalancingType;
import com.example.demo.loadBalancer.exceptions.InvalidRequestException;
import com.example.demo.loadBalancer.factories.LoadBalancingFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;

@Service
public class LoadBalancerService {

    private static LoadBalancingType loadBalancingType = LoadBalancingType.ROUND_ROBIN;

    @Value("${server.maxNumberOfRetries:3}")
    Integer maxNumberOfRetries;

    @Value("${server.backOffTimeInMillis:1000}")
    Integer backOffTimeInMillis;

    @Autowired
    LoadBalancingFactory loadBalancingFactory;

    // This function checks if the current seconds are in the specified ranges
    // i.e. between 20-30 and 50-60
    public static boolean isSecondsInSpecifiedRanges() {
        int seconds = LocalTime.now().getSecond();
        return (seconds >= 20 && seconds < 30) || (seconds >= 50 && seconds < 60);
    }

    public ServerResponseDTO getServerResponse() throws InvalidRequestException {
        int retries = 0;
        while (retries < maxNumberOfRetries) {
            try {
                LoadBalancingFactoryService loadBalancingService = loadBalancingFactory.getLoadBalancingService(loadBalancingType);
                Server server = loadBalancingService.getServer();
                if (!server.isHealthy() || shouldServerThrowError()) {
                    throw new InvalidRequestException("Server is not healthy");
                }
                server.incrementTotalConnectionsCount();
                return ServerResponseDTO.builder()
                        .response(server.getResponse())
                        .code(server.getCode())
                        .build();
            } catch (Exception e) {
                retries++;
                try {
                    Thread.sleep(backOffTimeInMillis);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new InvalidRequestException("Max retries exceeded");
    }

    // This function is stimulating server errors randomly
    public boolean shouldServerThrowError() {
        return Math.random() < 0.5 && isSecondsInSpecifiedRanges();
    }

    public String getLoadBalancerType() {
        return loadBalancingType.toString();
    }

    public void setLoadBalancerType(String newLoadBalancerType) throws InvalidRequestException {

        LoadBalancingType type = LoadBalancingType.fromString(newLoadBalancerType);
        if (ObjectUtils.isEmpty(type)) {
            throw new InvalidRequestException("Invalid Load Balancing Type");
        }

        loadBalancingType = type;
    }
}
