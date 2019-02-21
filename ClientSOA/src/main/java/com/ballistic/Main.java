package com.ballistic;

import com.ballistic.doa.query.FieldQuery;
import com.ballistic.doa.query.LocalQuery;
import java.util.*;

public class Main {

    public static void main(String args[]) {
    }

    public static LocalQuery getLocalQuery() {
        LocalQuery localQuery = new LocalQuery();
        localQuery.setFilter(getFilter());
        localQuery.setProjection(getProjection());
        localQuery.setSort(getSort());
        return localQuery;
    }

    private static Set<FieldQuery> getFilter() {
        Set<FieldQuery> filter = new HashSet<>();
        return filter;
    }

    private static Set<String> getProjection() {
        Set<String> projection = new HashSet<>();
        return projection;
    }

    private static Map<String, Integer> getSort() {
        HashMap<String, Integer> sort = new HashMap<>();
        return sort;
    }

 }