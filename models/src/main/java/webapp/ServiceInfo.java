package webapp;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@Entity
public class ServiceInfo {

    @Id
    private final @NonNull String version;

    private final @NonNull String description;
    private final @NonNull String title;
    private final @NonNull String contact;
    private final @NonNull String licence;
}
