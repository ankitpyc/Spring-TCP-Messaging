package org.messageQueues;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.config.TcpConnectionFactoryFactoryBean;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.messaging.MessageHandler;

@EnableIntegration
@Configuration
public class TCPConsumerConfig {
    public TcpConnectionFactoryFactoryBean client() {
        TcpConnectionFactoryFactoryBean factory = new TcpConnectionFactoryFactoryBean();
        factory.setType("client");
        factory.setHost("localhost"); // Server host
        factory.setPort(9999); // Server port
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "outputChannel")
    public MessageHandler clientOutboundAdapter(AbstractClientConnectionFactory client) {
        TcpSendingMessageHandler handler = new TcpSendingMessageHandler();
        handler.setConnectionFactory(client);
        return handler;
    }

    @MessagingGateway(defaultRequestChannel = "outputChannel")
    public interface TcpGateway {
        String sendAndReceive(String message);
    }
}
