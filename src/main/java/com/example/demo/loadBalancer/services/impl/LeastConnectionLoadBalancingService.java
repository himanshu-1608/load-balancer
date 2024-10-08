package com.example.demo.loadBalancer.services.impl;

import com.example.demo.loadBalancer.entities.Server;
import com.example.demo.loadBalancer.exceptions.InvalidRequestException;
import com.example.demo.loadBalancer.services.LoadBalancingFactoryService;
import com.example.demo.loadBalancer.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeastConnectionLoadBalancingService implements LoadBalancingFactoryService {

    @Autowired
    ServerService serverService;

    @Override
    public Server getServer() throws InvalidRequestException {
        List<Server> servers = serverService.getServers();

        Integer minConnectionsCount = Integer.MAX_VALUE;
        Server minConnectionServer = null;

        for (Server server : servers) {
            if (server.isHealthy() && server.getTotalConnectionsCount() < minConnectionsCount) {
                minConnectionsCount = server.getTotalConnectionsCount();
                minConnectionServer = server;
            }
        }

        if (minConnectionServer == null) {
            throw new InvalidRequestException("No healthy servers available");
        }

        return minConnectionServer;
    }

}
