package com.ega.datarepositorysevice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp.ServiceInfo;

public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, String> {
    ServiceInfo findDistinctFirstByVersion();
}
