package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessMethodsRepository extends JpaRepository<AccessMethods, String> {
    @Override
    Optional<AccessMethods> findById(String id);
}