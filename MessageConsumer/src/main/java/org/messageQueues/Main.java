package org.messageQueues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Main {

    int count = 1;

    @Autowired
    MyScheduledTask myScheduledTask;
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
        System.out.println("TCP Client Started");
    }

    @Bean
    public CommandLineRunner commandLineRunner(TCPConsumerConfig.TcpGateway tcpGateway) {
        return args -> {
            // Dummy message to start the client
            tcpGateway.send("Hello, Server will start sending messages!");
        };
    }
}