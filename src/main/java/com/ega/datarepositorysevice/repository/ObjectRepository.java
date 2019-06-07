package com.ega.datarepositorysevice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectRepository extends JpaRepository<Object, String> {
    @Override
    Optional<Object> findById(String id);
}
