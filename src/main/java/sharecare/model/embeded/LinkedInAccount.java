package sharecare.model.embeded;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
public class LinkedInAccount {
    String url;
    Boolean confirmed;
    LocalDateTime lastUpdated;
}

