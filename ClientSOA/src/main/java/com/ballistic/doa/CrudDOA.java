package com.ballistic.doa;

import java.util.List;

public interface CrudDOA<R> {

    public void save(R value) throws Exception;
    public void save(List<R> value) throws Exception;
    public void update(R value) throws Exception;
    public void update(List<R> value) throws Exception;
    public R findById(String id);

}
