package br.com.javapi.beertime.vehicles.common;

public class Constants {

    public static final String MESSAGE_TYPE_HEADER_NAME = "mq_message_type";
    public static final String VEHICLE_MESSAGE_TYPE_VALUE = "vehicle";
    public static final String RABBITMQ_QUEUE_NAME = "tba.vehicles.queue";
    public static final String RABBIT_EXCHANGE_NAME = "tba.vehicles.exchange";
    public static final String RABBIT_DLQ_EXCHANGE_NAME = "tba.dlq.exchange";
    public static final String RABBITMQ_DLQUEUE_NAME = "tba.dl.queue";

}
