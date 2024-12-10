package com.core.coffee.service;



import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;


public abstract class GenericService<T, ID> {

    public abstract ServiceResponse<?> create(T entity);

    //public abstract ServiceResponse<?> update(ID id, T entity);

    public abstract ServiceResponse<?> delete(ID id);

    public abstract ServiceResponse<?> getItem(ID id);

    public  abstract ServiceResponse<PagedResponse<?>> getAll(int page, int size);
}
