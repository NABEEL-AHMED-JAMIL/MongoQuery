package com.ballistic.doa;

import com.ballistic.pojo.Shipper;

import java.util.List;

public interface IShipperDOA extends CrudDOA<Shipper> {

    public static final String SHIPPER_ID = "shipperId";
    public static final String SHIPPER_NAME = "shipperName";
    public static final String PHONE = "phone";

    public List<Shipper> fetchAll();
}
