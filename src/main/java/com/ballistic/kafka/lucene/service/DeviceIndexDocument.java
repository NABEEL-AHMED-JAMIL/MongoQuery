package com.ballistic.kafka.lucene.service;

import com.ballistic.kafka.pojo.Device;
import java.util.List;

public class DeviceIndexDocument implements IIndexDocument<Device> {


    @Override
    public void saveIndex(Device document) {

    }

    @Override
    public void saveIndexes(List<Device> documents) {

    }

    @Override
    public Device findOnce(String key) {
        return null;
    }

    @Override
    public List<Device> findAll(String value) {
        return null;
    }

    @Override
    public void update(List<Device> document) {

    }

    @Override
    public Boolean deleteOnc(String key) {
        return null;
    }

    @Override
    public Boolean delete() {
        return null;
    }
}