package com.ballistic.kafka.broker;

import com.ballistic.kafka.pojo.Device;
import com.ballistic.kafka.pojo.Photo;
import com.ballistic.kafka.pojo.Status;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

/**
 * Note:- A Simple Single :- kafka Producer with kafka Consumer
 * */
public class Producer {

    private KafkaProducer<String, String> producer = null;
    private static volatile Boolean init = false;
    private ImageProcess imageProcess;
    private List<BufferedImage> bufferedImages;

    public Producer() {
        if(!init){
            System.out.println("Producer :- Constrictor..Init.");
            this.setProducer(new KafkaProducer<String, String>(getProperties()));
            this.imageProcess = new ImageProcess(6,6);
            this.bufferedImages = this.imageProcess.getSpriteImages();
            System.out.println("Producer :- Constrictor..End.");
            init = true;
        }
    }

    private Properties getProperties() {
        System.out.println("Producer :- Properties-Init.");
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "0");
        properties.put("retries", "1");
        properties.put("batch.size", "20971520");
        properties.put("linger.ms", "33");
        properties.put("max.request.size", "2097152");
        properties.put("compression.type", "gzip");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("kafka.topic", "demo1");
        System.out.println("Producer :- Properties-End.");
        return properties;
    }

    public KafkaProducer<String, String> getProducer() { return producer; }
    private void setProducer(KafkaProducer<String, String> producer) { this.producer = producer; }

    public static Integer getRandomNumberInRange(Integer min, Integer max) { return new Random().ints(min, (max + 1)).findFirst().getAsInt(); }

    private String getMessage(String id) throws UnsupportedEncodingException {
        try {
            System.out.println("New Message :- Creating");
            MsgKafka msgkafka = new MsgKafka();
            msgkafka.setId(id);
            msgkafka.setTimestamp(String.valueOf(new Timestamp(System.currentTimeMillis())));
            msgkafka.setData(Base64.getEncoder().encodeToString(String.valueOf(this.chunkDevices()).getBytes("utf-8")));
            String message = new Gson().toJson(msgkafka);
            System.out.println("+----------------------------------------------------------------------------------------+");
            System.out.println("Message :-" + message);
            return message;
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error :- " +  e.getLocalizedMessage());
            throw e;
        }
    }

    private List<Device> chunkDevices() throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        System.out.println("Chuck :- Start " + startTime);
        List<Device> devices = new ArrayList<>();
        int randeomChunk = this.getRandomNumberInRange(1, 1000);
        System.out.println("Random Chuck :- " + randeomChunk);
        for (Integer i = 0; i <= randeomChunk; i++) {
            System.out.println("+-----------------------------------------------------------------------------------");
            Device device = null;
            if(i%2 == 0) {
                device = new Device(
                    String.valueOf(UUID.randomUUID()), String.valueOf(UUID.randomUUID()).replaceAll("[^a-z]", ""),
                    String.valueOf(this.getRandomNumberInRange(1, 100)), new Date(), null,
                    this.getRandomNumberInRange(1, 2) == 1 ? Status.NEW : Status.SECOND);
            } else {
                /**
                 * Note:- Photo Object
                 * Bindery code will deserialize and print the part of image
                 * */
                String photoName = String.valueOf(UUID.randomUUID()).replaceAll("[^a-z]", "");
                String photoUrl = "https://ballistic.com/?photoName/" + photoName + "/photo="+
                        Base64.getEncoder().encodeToString
                                (String.valueOf(this.bufferedImages.get(this.getRandomNumberInRange(1, this.bufferedImages.size()-1))).getBytes("utf-8"));
                device = new Device(String.valueOf(UUID.randomUUID()), String.valueOf(UUID.randomUUID()).replaceAll("[^a-z]", ""),
                    String.valueOf(this.getRandomNumberInRange(1, 100)), new Date(), this.getRandomNumberInRange(1, 2) == 1 ? Status.NEW : Status.SECOND,
                    new Photo(photoName, photoUrl, String.valueOf(this.getRandomNumberInRange(100, 1000)), this.type(), new Date(), new Date()));
            }
            devices.add(device);
            System.out.println("Device :- " + device);
        }
        System.out.println("Chuck :- End :- " + (System.currentTimeMillis() - startTime)/1000 + "sec.");
        return devices;
    }

    private String type() {

        Integer type = getRandomNumberInRange(1, 3);
        if(type == 1) {
            return "image/png";
        } else if (type == 2) {
            return "image/jpg";
        } else {
            return "image/jpeg";
        }
    }

    public void startProducer() {
        try{
            String topic = null;
            while (true) {
                long startTime = System.currentTimeMillis();
                System.out.println("Producer :- Start " + startTime);
                if(topic == null) { topic = String.valueOf(this.getProperties().get("kafka.topic")); }
                String id = "device-" + UUID.randomUUID();
                String message = getMessage(id);
                System.out.println("Send :- " + message);
                this.getProducer().send(new ProducerRecord(topic, id , message));
                System.out.println("+----------------------------------------------------------------------------------");
                long stopTime = System.currentTimeMillis();
                long elapsedTime = (stopTime - startTime)/1000;
                System.out.println("Send :- " + "Successfully " + elapsedTime + " sec.");
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error :- " +  e.getLocalizedMessage());
        } finally {
            this.getProducer().close();
            System.out.println("Producer :- Close");
        }
    }

    public final static class ImageProcess {

        private static volatile Boolean init = false;
        private final String imagePath = "split.png";
        private BufferedImage spriteSheet;
        private Integer rows, cols = 0;
        // mean that in 1 image have 36 sprite
        private final Integer totalSprite = 36;
        private Integer width, height;
        private List<BufferedImage> spriteImages = new ArrayList<>(totalSprite);
        private File file;

        public ImageProcess() { }

        public ImageProcess(Integer rows, Integer cols) {
            if(!init) {
                if(!imagePath.contains("..")) {
                    this.file = new File(ClassLoader.getSystemResource(imagePath).getPath());
                    if(this.file != null && this.file.isFile()) {
                        try {
                            System.out.println("Read File :- "+ this.file);
                            this.setRows(rows);
                            this.setCols(cols);
                            this.setSpriteSheet(ImageIO.read(this.file));
                        } catch (IOException e) {
                            System.err.println("Error :- " + e.getLocalizedMessage());
                        }
                        init = true;
                        System.out.println("File Found and Assign");
                    }else {
                        System.err.println("Read File :- "+ imagePath + " Not Found.");
                        return;
                    }
                }else {
                    System.err.println("Error Wrong Path");
                }
            }
        }

        public int getRows() { return rows; }
        private void setRows(Integer rows) { this.rows = rows; }

        public int getCols() { return cols; }
        private void setCols(Integer cols) { this.cols = cols; }

        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }

        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }

        public BufferedImage getSpriteSheet() { return spriteSheet; }
        public void setSpriteSheet(BufferedImage spriteSheet) { this.spriteSheet = spriteSheet; }

        // Note:- Sprite the image's and send the Boolean => if
        private List<BufferedImage> getSpriteImages() {

            if(this.getWidth() == null) { this.setWidth(this.getSpriteSheet().getWidth()/this.getCols()); }
            if(this.getHeight() == null) { this.setHeight(this.getSpriteSheet().getHeight()/this.getRows()); }

            int x = 0;
            int y = 0;
            for (int index = 0; index < totalSprite; index++) {
                this.spriteImages.add(this.getSpriteSheet().getSubimage(x, y, width, height));
                x += width;
                if (x >= width * cols) {
                    x = 0;
                    y += height;
                }
            }
            return this.spriteImages;
        }
    }

    public static void main(String[] args){
        Producer producer = new Producer();
        producer.startProducer();

    }
}
