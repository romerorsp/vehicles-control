package br.com.javapi.beertime.vehicles.consumer.integration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@ImportAutoConfiguration(RabbitAutoConfiguration.class)
public class MQConfiguration {

    @Value("${vehicles.mq.consumer.queues}")
    private String[] amqpQueues;
    
    @Value("${vehicles.mq.consumer.queue.size:200}")
    private int queueSize;
    
    @Autowired
    private VehicleStateTransformer vehicleStateTransformer;

    @Bean(name="vehiclesInputChannel")
    public MessageChannel createVehiclesInputChannel() {
        return MessageChannels.queue(queueSize).get();
    }
    
    public IntegrationFlow amqpFlow(@Autowired final ConnectionFactory connectionFactory,
                                                             @Autowired @Qualifier("vehiclesInputChannel") final MessageChannel channel) {
        return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, amqpQueues))
                                               .transform(vehicleStateTransformer)
                                               .channel(channel) .get();
    }
}
