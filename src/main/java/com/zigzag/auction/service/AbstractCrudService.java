package com.zigzag.auction.service;

import java.util.List;

public interface AbstractCrudService<T, I> {
    T get(I id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    void delete(I id);
}
