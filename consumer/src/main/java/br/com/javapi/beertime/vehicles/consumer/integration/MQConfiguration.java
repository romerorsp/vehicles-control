package br.com.javapi.beertime.vehicles.consumer.integration;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import br.com.javapi.beertime.vehicles.common.Constants;

@Configuration
@ImportAutoConfiguration(RabbitAutoConfiguration.class)
public class MQConfiguration {

    @Value("${vehicles.mq.consumer.queue.size:200}")
    private int queueSize;
    
    @Autowired
    private VehicleStateTransformer vehicleStateTransformer;

    @Bean(name="vehiclesInputChannel")
    public MessageChannel createVehiclesInputChannel() {
        return MessageChannels.queue(queueSize).get();
    }
    
    @Bean(name="vehicleXChange")
    public Exchange createExchange() {
        return ExchangeBuilder.directExchange(Constants.RABBIT_EXCHANGE_NAME)
                              .durable(true)
                              .build();
    }


    @Bean(name="vehicleQ")
    public Queue createQueue() {
        return QueueBuilder.durable(Constants.RABBITMQ_QUEUE_NAME)
                           .withArgument("x-dead-letter-exchange", Constants.RABBIT_DLQ_EXCHANGE_NAME)
                           .build();
    }

    @Bean("vehicleQXChangeBinding")
    public Binding createBinding(@Autowired @Qualifier("vehicleQ") final Queue queue,
                                 @Autowired @Qualifier("vehicleXChange") final Exchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .with("")
                             .noargs();
    }
    
    @Bean(name="vehicleDLQXChange")
    public Exchange createDLQExchange() {
        return ExchangeBuilder.directExchange(Constants.RABBIT_DLQ_EXCHANGE_NAME)
                              .durable(true)
                              .build();
    }


    @Bean(name="vehicleDLQ")
    public Queue createDLQueue() {
        return QueueBuilder.durable(Constants.RABBITMQ_DLQUEUE_NAME)
                           .withArgument("x-dead-letter-exchange", Constants.RABBIT_DLQ_EXCHANGE_NAME)
                           .build();
    }

    @Bean("vehicleDLQXChangeBinding")
    public Binding createDLQBinding(@Autowired @Qualifier("vehicleDLQ") final Queue queue,
                                    @Autowired @Qualifier("vehicleDLQXChange") final Exchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .with("")
                             .noargs();
    }
    
    @Bean("amqpConsumerFlow")
    public IntegrationFlow amqpFlow(@Autowired final ConnectionFactory connectionFactory,
                                    @Autowired @Qualifier("vehiclesInputChannel") final MessageChannel channel,
                                    @Autowired @Qualifier("vehicleQ") final Queue queue) {
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, queue))
                               .transform(vehicleStateTransformer)
                               .channel(channel)
                               .get();
    }
    
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata poller = new PollerMetadata();
        poller.setTrigger(new PeriodicTrigger(1, TimeUnit.MILLISECONDS));
        return poller;
    }
}
