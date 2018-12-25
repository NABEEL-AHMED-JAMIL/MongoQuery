package com.ballistic.kafka.mongdb;

import com.ballistic.kafka.pojo.Device;
import com.ballistic.kafka.pojo.Status;
import java.util.Date;
import java.util.List;

public class DeviceManager {

    private IDeviceDAO deviceDAO = null;

    public DeviceManager(IDeviceDAO deviceDAO) {
        System.out.println("DeviceManger :- Constrictor..Init.");
        this.deviceDAO = deviceDAO;
        System.out.println("DeviceManger :- Constrictor..End.");
    }
    // here we check and send all data
    public void save(List<Device> devices) { this.deviceDAO.save(devices); }
    public void save(Device device) { this.deviceDAO.save(device); }

    public void update(List<Device> devices) { this.deviceDAO.update(devices); }

    public void update(Device device) { this.deviceDAO.update(device); }

    public Device findById(String id) { return this.deviceDAO.findById(id); }

    public List<Device> getAllDevices() { return this.deviceDAO.getAllDevices(); }

    public List<Device> fetchDevicesByPricesOrSize(String field,String value, Integer option) {
        return this.deviceDAO.fetchDevicesByPricesOrSize(field,value,option);
    }

    public List<Device> fetchAllByDeviceStatus(Status status) { return this.deviceDAO.fetchAllByDeviceStatus(status); }

    public List<Device> fetchAllByDates(Date to, Date from) { return this.deviceDAO.fetchAllByDates(to, from); }

}
