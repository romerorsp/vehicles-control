package br.com.javapi.beertime.vehicles.websocket.integration;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.javapi.beertime.vehicles.common.Constants;

@Configuration
public class IntegrationFlowConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationFlowConfiguration.class);
    
    @Value("${broker.mq.producer.queue.size:100}")
    private String size;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Bean("vehicleStateChannel")
    public MessageChannel createVehicleStateChannel() {
        return MessageChannels.queue(size).get();
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
    public Binding createBinding(@Autowired @Qualifier("vehicleQ") Queue queue,
                                @Autowired @Qualifier("vehicleXChange") Exchange exchange) {
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
    public Binding createDLQBinding(@Autowired @Qualifier("vehicleDLQ") Queue queue,
                                @Autowired @Qualifier("vehicleDLQXChange") Exchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .with("")
                             .noargs();
    }
    
    @Bean("amqpProducerFlow")
    public IntegrationFlow amqpFlow(@Autowired final ConnectionFactory connectionFactory,
                                    @Autowired final AmqpTemplate amqpTemplate,
                                    @Autowired @Qualifier("vehicleStateChannel") final MessageChannel channel) {
        return IntegrationFlows.from(channel)
                               .transform(value -> {
                                    try {
                                        return MAPPER.writeValueAsString(value);
                                    } catch (JsonProcessingException e) {
                                        LOGGER.error("It wasn't possible to write the value [{}] as string", e);
                                        return null;
                                    }
                               })
                               .handle(Amqp.outboundAdapter(amqpTemplate)
                                           .routingKey("")
                                           .exchangeName(Constants.RABBIT_EXCHANGE_NAME))
                               .get();
    }
    
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata poller = new PollerMetadata();
        poller.setTrigger(new PeriodicTrigger(1, TimeUnit.MILLISECONDS));
        return poller;
    }
}
