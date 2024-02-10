package org.messageQueues;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTask implements CommandLineRunner {

    TCPConsumerConfig.TcpGateway tcpGateway;

    public MyScheduledTask (TCPConsumerConfig.TcpGateway tcpGateway){
        this.tcpGateway = tcpGateway;
    }
    int count = 0;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Scheduler");
    }

    @Scheduled(fixedRate = 5000) // Execute every 5 seconds
    public void myPeriodicTask() {
        // This method will be executed periodically
        sendMessage();
    }

    public void sendMessage(){
        tcpGateway.send(" Message : " + count++);
    }
}
