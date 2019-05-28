package webapp;

import org.springframework.data.repository.CrudRepository;

public interface ServiceInfoRepository extends CrudRepository<ServiceInfo, String> {
    ServiceInfo findDistinctFirstByVersion();
}
