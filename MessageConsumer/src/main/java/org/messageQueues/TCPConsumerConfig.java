package org.messageQueues;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.ip.config.TcpConnectionFactoryFactoryBean;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;

@Configuration
@EnableIntegration
public class TCPConsumerConfig {

    @Bean
    public TcpConnectionFactoryFactoryBean client() {
        TcpConnectionFactoryFactoryBean factory = new TcpConnectionFactoryFactoryBean();
        factory.setType("client");
        factory.setHost("localhost"); // Specify the server machine IP or hostname
        factory.setPort(9999); // Specify the server machine port
        return factory;
    }

    @Bean
    public MessageChannel messageChannel(){
        return new QueueChannel();
    }

    @MessagingGateway(defaultRequestChannel = "outputChannel")
    public interface TcpGateway {
        void send(String message);
    }

    @Bean
    @ServiceActivator(inputChannel = "outputChannel")
    public MessageHandler clientOutboundAdapter(AbstractClientConnectionFactory client) {
        TcpSendingMessageHandler handler = new TcpSendingMessageHandler();
        handler.setConnectionFactory(client);
        return handler;
    }
}