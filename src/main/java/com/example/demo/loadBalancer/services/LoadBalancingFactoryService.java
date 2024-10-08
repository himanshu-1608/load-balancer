package com.example.demo.loadBalancer.services;

import com.example.demo.loadBalancer.entities.Server;
import com.example.demo.loadBalancer.exceptions.InvalidRequestException;

public interface LoadBalancingFactoryService {

    Server getServer() throws InvalidRequestException;

}
