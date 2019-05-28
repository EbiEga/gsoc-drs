package webapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, String> {
    ServiceInfo findDistinctFirstByVersion();
}
