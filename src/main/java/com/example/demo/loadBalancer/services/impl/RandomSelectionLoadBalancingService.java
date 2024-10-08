package com.example.demo.loadBalancer.services.impl;

import com.example.demo.loadBalancer.entities.Server;
import com.example.demo.loadBalancer.exceptions.InvalidRequestException;
import com.example.demo.loadBalancer.services.LoadBalancingFactoryService;
import com.example.demo.loadBalancer.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RandomSelectionLoadBalancingService implements LoadBalancingFactoryService {

    @Autowired
    ServerService serverService;

    @Override
    public Server getServer() throws InvalidRequestException {
        int min = 0;
        int serverCount = serverService.getServers().size();
        if (serverCount == 0) {
            throw new InvalidRequestException("No servers available");
        }

        int randomIndex = (int) (Math.random() * (serverCount - 1)) + min;
        return serverService.getServerByIndex(randomIndex);
    }

}
