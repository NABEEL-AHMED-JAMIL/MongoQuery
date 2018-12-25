package com.ballistic.kafka.broker;

import com.ballistic.kafka.mongdb.DbConnection;
import com.ballistic.kafka.mongdb.DeviceDAO;
import com.ballistic.kafka.mongdb.DeviceManager;
import com.ballistic.kafka.pojo.Device;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Note :- Consumer Class
 * Name => Nabeel Ahmed
 * Email => nabeel.amd93@gmail.com
 * */
public class Consumer {

    private DbConnection dbConnection = new DbConnection("localhost:27017", "kdb");
    private DeviceManager deviceManager = null;
    private KafkaConsumer<String, String> consumer = null;
    private List<Device> devices = null;
    private static volatile Boolean init = false;

    public Consumer() {
        if(!init){
            System.out.println("Consumer :- Constrictor..Init.");
            this.setConsumer(new KafkaConsumer<String, String>(this.getProperties()));
            this.setDeviceManager(new DeviceManager(new DeviceDAO(dbConnection)));
            System.out.println("Consumer :- Constrictor..End.");
            init = true;
        }
    }

    public DeviceManager getDeviceManager() { return deviceManager; }
    private void setDeviceManager(DeviceManager deviceManager) { this.deviceManager = deviceManager; }

    private Properties getProperties() {
        System.out.println("Consumer :- Properties-Init.");
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("kafka.topic" , "demo1");
        properties.put("compression.type" , "gzip");
        properties.put("key.deserializer" , "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer" , "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("max.partition.fetch.bytes", "2097152");
        properties.put("max.poll.records", "500");
        properties.put("group.id", "test-group");
        System.out.println("Consumer :- Properties-End.");
        return properties;
    }

    public KafkaConsumer<String, String> getConsumer() { return consumer; }
    private void setConsumer(KafkaConsumer<String, String> consumer) { this.consumer = consumer; }

    public MsgKafka decodeMsg(String json) throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        System.out.println("New Message :- Decode");
        Gson gson = new Gson();
        MsgKafka msg = gson.fromJson(json, MsgKafka.class);
        byte[] decoderData = Base64.getDecoder().decode(msg.getData());
        msg.setData(new String(decoderData, "utf-8"));
        System.out.println("+----------------------------------------------------------------------------------------+");
        System.out.println("Message :-" + msg);
        try {
            this.devices = new ArrayList<>();
            this.devices = gson.fromJson(msg.getData(), new TypeToken<List<Device>>(){}.getType());
        }catch (Exception e) {
            System.err.println("Error :- Data Parse Exception");
        }
        System.out.println("+----------------------------------------------------------------------------------------+");
        System.out.println("Msg Decode :- " + "Successfully " + (System.currentTimeMillis() - startTime)/1000 + " sec.");
        return msg;
    }

    public void startConsumer() {
        try{
            List<String> topics = Arrays.asList(this.getProperties().getProperty("kafka.topic"));
            this.getConsumer().subscribe(topics);
            System.out.println("Subscribed to topic " + topics.toString());
            while (true){
                long startTime = System.currentTimeMillis();
                System.out.println("Consumer :- Start " + startTime);
                ConsumerRecords<String, String> records = this.getConsumer().poll(10);
                if(!records.isEmpty()) {
                    for (ConsumerRecord<String, String> record: records) {
                        System.out.println("+-----------------------------------------------------------------------------+");
                        System.out.printf("partition = %s, offset = %d, key = %s, value = %s\n", record.partition(), record.offset(), record.key(), decodeMsg(record.value()).getData());
                        if(devices.size() > 0) {
                            if(devices.size() > 10 ) {
                                this.getDeviceManager().save(devices);
                            } else {
                                devices.parallelStream().forEach(device -> {
                                    if(device.getParchesDate() == null) { device.setParchesDate(new Date()); }
                                    this.getDeviceManager().save(device);
                                });
                            }
                        }
                    }
                    System.out.println("Process Time :- " + (System.currentTimeMillis() - startTime)/1000 + " sec.");
                }else {
                    System.out.println("Wait...ing");
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error " +  e.getLocalizedMessage());
        } finally {
            this.getConsumer().close();
            System.out.println("Producer :- Close");
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.startConsumer();
    }
}
