package com.example.demo.loadBalancer.factories;

import com.example.demo.loadBalancer.enums.LoadBalancingType;
import com.example.demo.loadBalancer.exceptions.InvalidRequestException;
import com.example.demo.loadBalancer.services.LoadBalancingFactoryService;
import com.example.demo.loadBalancer.services.impl.LeastConnectionLoadBalancingService;
import com.example.demo.loadBalancer.services.impl.RandomSelectionLoadBalancingService;
import com.example.demo.loadBalancer.services.impl.RoundRobinLoadBalancingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadBalancingFactory {

    @Autowired
    RoundRobinLoadBalancingService roundRobinLoadBalancingService;

    @Autowired
    RandomSelectionLoadBalancingService randomSelectionLoadBalancingService;

    @Autowired
    LeastConnectionLoadBalancingService leastConnectionLoadBalancingService;

    public LoadBalancingFactoryService getLoadBalancingService(LoadBalancingType loadBalancingType) throws InvalidRequestException {
        switch (loadBalancingType) {
            case ROUND_ROBIN:
                return roundRobinLoadBalancingService;
            case RANDOM_SELECTION:
                return randomSelectionLoadBalancingService;
            case LEAST_CONNECTION:
                return leastConnectionLoadBalancingService;
            default:
                throw new InvalidRequestException("Invalid Load Balancing Type");
        }
    }

}
