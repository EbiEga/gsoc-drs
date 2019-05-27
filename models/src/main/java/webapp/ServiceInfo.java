package webapp;



import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "service_info")
public class ServiceInfo {
    @Id
    private  String version;
    private  String description;
    private  String title;
    private  String contact;
    private  String licence;

    public ServiceInfo(String version) {
        version = null;
    }
}
