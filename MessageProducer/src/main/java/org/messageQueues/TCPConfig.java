package org.messageQueues;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.config.TcpConnectionFactoryFactoryBean;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.nio.charset.StandardCharsets;

@EnableIntegration
@Configuration
public class TCPConfig {
    @Bean
    public TcpConnectionFactoryFactoryBean server() {
        TcpConnectionFactoryFactoryBean factory = new TcpConnectionFactoryFactoryBean();
        factory.setType("server");
        factory.setPort(9999); // Change the port if needed
        return factory;
    }

    @Bean
    public TcpReceivingChannelAdapter inboundAdapter(AbstractServerConnectionFactory server) {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(server);
        adapter.setOutputChannel(inputChannel());
        return adapter;
    }

    @Bean
    public MessageChannel inputChannel() {
        return new QueueChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public MessageHandler handleIncomingMessage() {
        return message -> {
            // Handle the incoming message
            byte[] payload = (byte[]) message.getPayload();
            String receivedMessage = new String(payload, StandardCharsets.UTF_8);

            System.out.println("Received message from client: " + receivedMessage);
        };
    }



    @Bean
    public TcpSendingMessageHandler outboundAdapter(AbstractServerConnectionFactory server) {
        TcpSendingMessageHandler handler = new TcpSendingMessageHandler();
        handler.setConnectionFactory(server);
        return handler;
    }
}
