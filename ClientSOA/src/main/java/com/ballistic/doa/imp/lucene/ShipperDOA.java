package com.ballistic.doa.imp.lucene;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.IShipperDOA;
import com.ballistic.pojo.Shipper;
import java.util.List;

public class ShipperDOA  extends LuceneDBContext implements IShipperDOA {

    private LuceneDBContext luceneDBContext;

    public ShipperDOA() {}

    public ShipperDOA(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    @Override
    public List<Shipper> fetchAll() {
        return null;
    }

    @Override
    public void save(Shipper value) throws Exception {

    }

    @Override
    public void save(List<Shipper> value) throws Exception {

    }

    @Override
    public void update(Shipper value) throws Exception {

    }

    @Override
    public void update(List<Shipper> value) throws Exception {

    }

    @Override
    public Shipper findById(String id) {
        return null;
    }

}
