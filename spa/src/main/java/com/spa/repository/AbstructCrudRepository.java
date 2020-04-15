package com.spa.repository;

import com.spa.domain.Sort;

import java.util.List;

public interface AbstructCrudRepository<I, E> {

    I save(E entity);

    I update(E entity);

    I delete(I id);

    E findOne(I id);

    List<E> findAll(I limit, I offset, Sort sort);
}
