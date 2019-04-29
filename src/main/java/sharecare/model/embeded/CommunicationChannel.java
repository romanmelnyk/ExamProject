package sharecare.model.embeded;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class CommunicationChannel {
    String url;
    String token;
}
