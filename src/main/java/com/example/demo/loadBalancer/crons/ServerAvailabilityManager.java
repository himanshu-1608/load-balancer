package com.example.demo.loadBalancer.crons;

import com.example.demo.loadBalancer.services.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServerAvailabilityManager {

    @Autowired
    ServerService serverService;

    // Every 30 seconds, run this method
    @Scheduled(cron = "*/30 * * * * *")
    public void manageUnhealthyAndHealthyServers() {
        log.info("Starting scheduled task to remove unhealthy servers...");
        serverService.removeUnhealthyServers();
        log.info("Unhealthy servers removed.");

        log.info("Starting scheduled task to move healthy servers...");
        serverService.moveHealthyServersBack();
        log.info("Healthy servers moved back.");
    }

}
