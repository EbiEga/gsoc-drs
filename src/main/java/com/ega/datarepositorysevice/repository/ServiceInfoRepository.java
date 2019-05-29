package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, String> {
    ServiceInfo findDistinctFirstByVersion();
}
