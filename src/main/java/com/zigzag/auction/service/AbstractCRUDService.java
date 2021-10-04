package com.zigzag.auction.service;

import java.util.List;

public interface AbstractCRUDService<T, Id> {
    T get(Id id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    void delete(Id id);
}
