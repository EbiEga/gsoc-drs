package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BundleRepository extends JpaRepository<Bundle, String> {
    @Override
    Optional<Bundle> findById(String id);
}
