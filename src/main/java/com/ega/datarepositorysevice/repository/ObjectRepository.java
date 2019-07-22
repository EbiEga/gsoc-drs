package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectRepository extends JpaRepository<Object, Long> {
    @Override
    Optional<Object> findById(Long id);

}
