package com.example.service;

import java.util.Optional;

public interface IGenericService<B> {
    Iterable<B> getAll();

    Optional<B> getById(Long id);

    B save(B b);

    void delete(Long id);
}
