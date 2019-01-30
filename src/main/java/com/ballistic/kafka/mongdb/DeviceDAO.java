package com.ballistic.kafka.mongdb;

import com.ballistic.kafka.pojo.Device;
import com.ballistic.kafka.pojo.Photo;
import com.ballistic.kafka.pojo.Status;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.mongodb.MongoException;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class DeviceDAO implements IDeviceDAO {

    private static final String DEV_NAME = "devName";
    private static final String PRICE = "price";
    private static final String STATUS = "status";
    private static final String SALEDATE = "saleDate";
    private static final String PHOTO = "photo";
    private static final String PHOTONAME = "photoName";
    private static final String PHOTOURL = "photoUrl";
    private static final String SIZE = "size";
    private static final String TYPE = "type";
    private static final String CREATED = "created";
    private static final String UPDATE = "update";

    private DbConnection dbConnection = null;

    /**
     * Note:- Duration Change from 15 to 1
     * Reason:- Due to Consumer update the Db on each second so we lime the cache evict time '1' mint
     * Issue:- data-retrieve => solve
     * */
    private static Cache<Status, List<Device>> fetchAllByDeviceStatusCache =
            CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(1, TimeUnit.MINUTES).build();
    private static Cache<String, List<Device>> fetchDevicesByPricesOrSizeCache =
            CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(1, TimeUnit.MINUTES).build();

    public DeviceDAO(DbConnection dbConnection) { this.dbConnection = dbConnection; }

    // Test-Done
    @Override
    public void save(List<Device> devices) {
        long startTime = System.currentTimeMillis();
        this.dbConnection.getDb().save(devices);
        System.out.println("Db Store Process Time :- " + (System.currentTimeMillis() - startTime)/1000 + " sec.");
    }

    // Test-Done
    @Override
    public void save(Device device) {
        long startTime = System.currentTimeMillis();
        this.dbConnection.getDb().save(device);
        System.out.println("Db Store Process Time :- " + (System.currentTimeMillis() - startTime)/1000 + " sec.");
    }

    // Test-Done
    @Override
    public void update(List<Device> devices) {
        long startTime = System.currentTimeMillis();
        if(!devices.equals(null) && devices.size() > 0) {
            devices.parallelStream().forEach(device -> { update(device); });
        }else {
            System.err.println("Device's have Null Object So Update Process Fail");
        }
        System.out.println("Db Update Process Time :- " + (System.currentTimeMillis() - startTime)/1000 + " sec.");
    }

    // Test-Done
    @Override
    public void update(Device device) {
        long startTime = System.currentTimeMillis();
        if(!device.equals(null)) {
            if(device.getId() != null) {
                try {
                    final Query<Device> findQuery = this.dbConnection.getDb().createQuery(Device.class).field(Mapper.ID_KEY).
                            equal(device.getId()).disableValidation();
                    UpdateOperations<Device> updateOps = this.dbConnection.getDb().createUpdateOperations(Device.class);

                    if(device.getDevName() != null) { updateOps.set(DEV_NAME, device.getDevName()); }

                    try {
                        if(device.getPrice() != null && Double.valueOf(device.getPrice()) > 0) {
                            updateOps.set(PRICE, device.getPrice());
                        }
                    }catch (NumberFormatException e) { System.err.println("Error " + e.getLocalizedMessage()); }

                    if(device.getStatus() != null) { updateOps.set(STATUS, device.getStatus()); }

                    if(device.getSaleDate() != null && (device.getParchesDate() != null && device.getParchesDate().
                            before(device.getSaleDate()))) {
                        updateOps.set(SALEDATE, device.getStatus().getValue());
                    }

                    if(device.getPhoto() != null) {

                        Photo photo = device.getPhoto();

                        if (photo.getPhotoName() != null) { updateOps.set(PHOTO+"."+PHOTONAME, photo.getPhotoName()); }

                        if (photo.getPhotoUrl() != null && photo.getPhotoName() != null) { updateOps.set(PHOTO+"."+PHOTOURL, photo.getPhotoUrl()); }

                        if (photo.getSize() != null) { updateOps.set(PHOTO+"."+SIZE, photo.getSize()); }

                        if (photo.getType() != null) { updateOps.set(PHOTO+"."+TYPE, photo.getType()); }

                        if (photo.getCreated() != null) { updateOps.set(PHOTO+"."+CREATED, photo.getCreated()); }

                        if ((photo.getSize() != null && photo.getCreated() != null) && (photo.getUpdate().after(photo.getCreated()))) {
                            updateOps.set(PHOTO+"."+UPDATE, photo.getUpdate());
                        }
                    }
                    this.dbConnection.getDb().update(findQuery, updateOps, true);

                }catch (MongoException e) {
                    System.err.println("Error " + e.getLocalizedMessage());
                } catch (Exception e) {
                    System.err.println("Error " + e.getLocalizedMessage());
                }
            } else {
                System.err.println("Zero Document fetch..");
            }
        }else {
            System.err.println("Device have Null Object So Update Process Fail");
        }
        System.out.println("Db Update Process Time :- " + (System.currentTimeMillis() - startTime)/ 1000 + " sec.");
    }

    // Test-Done
    @Override
    public Device findById(String id) {
        Device device = new Device();
        if(id != null) {
            device = this.dbConnection.getDb().createQuery(Device.class).field(Mapper.ID_KEY).equal(id).get();
        }
        return device;
    }

    // Test-Done
    @Deprecated
    @Override
    public List<Device> getAllDevices() {
        List<Device> devices = Lists.newArrayList();
        try {
            devices = this.dbConnection.getDb().find(Device.class).asList();
            return devices;
        }catch (OutOfMemoryError e) {
            System.out.println("Error " + e.getLocalizedMessage());
            return devices;
        }
    }

    // Test-Done
    @Override
    public List<Device> fetchDevicesByPricesOrSize(String field,String value,Integer option) {
        System.out.println("Fetch By " + field + " value " +  value + " option " + option);
        List<Device> devices = Lists.newArrayList();
        if((field != null && value != null) && option != null) {
            if(field.equalsIgnoreCase(SIZE) || field.equalsIgnoreCase(PRICE)) {
                if(field.equalsIgnoreCase(SIZE)) { field = PHOTO+"."+field; }
                try {
                    DbConnection dbConnection = this.dbConnection;
                    String finalField = field;
                    devices = fetchDevicesByPricesOrSizeCache.get(value, new Callable<List<Device>>() {
                        @Override
                        public List<Device> call() throws Exception {
                            List<Device> devices = Lists.newArrayList();
                            System.out.println("Fetch From Db");
                            if(option == 0) { // equal
                                devices = dbConnection.getDb().createQuery(Device.class).
                                    field(finalField).equal(value).asList();
                            }else if (option == 1) { // greater then equal
                                devices = dbConnection.getDb().createQuery(Device.class).
                                    field(finalField).greaterThanOrEq(value).asList();
                            } else if(option == -1) { // less then equal
                                devices = dbConnection.getDb().createQuery(Device.class).
                                    field(finalField).lessThanOrEq(value).asList();
                            }
                            return devices;
                        }
                    });

                }catch (NumberFormatException e) {
                    System.err.println("Error " + e.getLocalizedMessage());
                }catch (Exception e) {
                    System.out.println("Error " + e.getLocalizedMessage());
                }
            } else {
                System.err.println("Search field wrong it's should be 'size' either 'price'");
            }
        }
        return devices;
    }

    // Test-Done
    @Override
    public List<Device> fetchAllByDeviceStatus(Status status) {
        System.out.println("Fetch By " + status);
        List<Device> devices = Lists.newArrayList();
        try {
            DbConnection dbConnection = this.dbConnection;
            devices = fetchAllByDeviceStatusCache.get(status, new Callable<List<Device>>() {
                @Override
                public List<Device> call() throws Exception {
                    List<Device> devices = Lists.newArrayList();
                    devices = dbConnection.getDb().createQuery(Device.class).
                            field(STATUS).equal(status).asList();
                    System.out.println("Fetch From Db");
                    return devices;
                }
            });
        }catch (Exception e) {
            System.err.println("Error " + e.getLocalizedMessage());
        }
        return devices;
    }

    // under-process => not yet complete
    @Override
    public List<Device> fetchAllByDates(Date from, Date to) {
        List<Device> devices = Lists.newArrayList();
        if(to != null && from != null) {
            if(from.before(to)) {
                try {
                    DbConnection dbConnection = this.dbConnection;
                    // work like as id's and second time if new id's
                    String finalField = to + "_" + from;
                    devices = fetchDevicesByPricesOrSizeCache.get(finalField, new Callable<List<Device>>() {
                        @Override
                        public List<Device> call() throws Exception {
                            List<Device> devices = Lists.newArrayList();
                            devices = dbConnection.getDb().createQuery(Device.class).
                                field("parchesDate").
                                greaterThanOrEq(new Date()).
                                field("parchesDate").lessThanOrEq(new Date()).asList();
                            return devices;
                        }
                    });
                } catch (Exception e) {
                    System.out.println("Error " + e.getLocalizedMessage());
                }
            }
        }
        return devices;
    }


    // Test-Case Runner
    public static void main(String args[]) throws InterruptedException, ExecutionException, ParseException {

        /**
         *
         *         DbConnection dbConnection = new DbConnection("localhost:27017", "kdb");
         *         IDeviceDAO ideviceDAO = new DeviceDAO(dbConnection);
         *         Date from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse("2018-11-13 20:40:00.000");
         *         Date to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse("2018-11-13 20:40:01.000");
         *         System.out.println("Size " + ideviceDAO.fetchAllByDates(from, to).size());
         *         while (true) {
         *             long startTime = System.currentTimeMillis();
         *             System.out.println("+--------------------------------+");
         *             System.out.println("Size "+ideviceDAO.fetchDevicesByPricesOrSize("price", String.valueOf(Producer.getRandomNumberInRange(1, 100)), 0).size());
         *             // System.out.println("Size " + ideviceDAO.getAllDevices().size());
         *             System.out.println("Total Fetch Time " + (System.currentTimeMillis()-startTime)/1000 + " sec.");
         *         }
         *
         *         while (true) {
         *             long startTime = System.currentTimeMillis();
         *             ExecutorService executorService = Executors.newSingleThreadExecutor();
         *             Callable<List<Device>> callable = () -> {
         *                 System.out.println("Entered Callable");
         *                 Thread.sleep(200);
         *                 return ideviceDAO.fetchAllByDeviceStatus(Status.SECOND);
         *             };
         *             Future<List<Device>> future = executorService.submit(callable);
         *             System.out.println(future.get().size());
         *             //future.get().parallelStream().forEach(System.out::println);
         *             System.out.println("Total Fetch Time " + (System.currentTimeMillis()-startTime)/1000 + " sec.");
         *             executorService.shutdown();
         *         }
         *         ideviceDAO.save(new Device("baffbeaa", "85",new Date(), new Date(), Status.NEW));
         *         ideviceDAO.update(new Device("c9ea8b92-43df-43a7-8de8-4a6b717b9f71", "nabeel.amd93", "kjlj"));
         *         ideviceDAO.save(null);
         * */
    }
}
