package com.ballistic.broker;

import java.util.concurrent.ExecutionException;

public interface IConsumer {


    /**
     * create configuration for the producer
     * consult Kafka documentation for exact meaning of each configuration parameter
     */
    void configure(String brokerList, String sync);

    /* start the consumer */
    void start();

    /**
     * received record and send to some where
     * because the key is null, data will be sent to a random partition.
     * exact behavior will be different depending on producer implementation
     */
    void consumer(String s) throws ExecutionException, InterruptedException;

    /* stop the consumer */
    void close();
}
