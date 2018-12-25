package com.ballistic.broker;

import java.util.concurrent.ExecutionException;

public class Consumer implements IConsumer {

    public static volatile Boolean switcher = false;

    @Override
    public void configure(String brokerList, String sync) {}

    @Override
    public void start() {}

    @Override
    public void consumer(String s) throws ExecutionException, InterruptedException {}

    @Override
    public void close() {}

}
