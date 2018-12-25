package com.ballistic.kafka.mongdb;

import com.ballistic.kafka.pojo.Device;
import com.ballistic.kafka.pojo.Status;
import java.util.Date;
import java.util.List;

public interface IDeviceDAO {

    // Save
    public void save(List<Device> devices);
    public void save(Device device);

    // Update
    public void update(List<Device> devices);
    public void update(Device device);

    // Fetch
    public Device findById(String id);
    public List<Device> getAllDevices();
    public List<Device> fetchDevicesByPricesOrSize(String field ,String value, Integer option);
    public List<Device> fetchAllByDeviceStatus(Status status);
    public List<Device> fetchAllByDates(Date to, Date from);
}
