package sharecare.model.embeded;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Feedback {
    Long rating;
    String message;
}
