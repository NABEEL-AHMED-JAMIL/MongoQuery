package com.ballistic.broker;

import java.util.concurrent.ExecutionException;

public class Producer implements IProducer {

    public static volatile Boolean switcher = false;

    @Override
    public void configure(String brokerList, String sync) {}

    @Override
    public void start() {}

    @Override
    public void produce(String s) throws ExecutionException, InterruptedException {}

    @Override
    public void close() {}

}
