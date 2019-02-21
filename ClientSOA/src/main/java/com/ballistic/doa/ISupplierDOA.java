package com.ballistic.doa;

import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Supplier;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISupplierDOA extends CrudDOA<Supplier> {
    
    public static final String SUPPLIER_ID = "supplierId";
    public static final String SUPPLIER_NAME = "supplierName";
    public static final String CONTACT_NAME = "contactName"; // uniq handle my manwal
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String POSTAL_CODE = "postalCode";
    public static final String COUNTRY = "country";
    public static final String PHONE = "phone"; // add the pattern // uniq handle by manwal

    public Map<String, Set<String>> fetchOptionalQuery(Integer type);
    public List<Supplier> fetchAllByQuery(LocalQuery localQuery);
    public List<Supplier> fetchAll();

}
