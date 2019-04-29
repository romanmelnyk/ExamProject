package sharecare.model.embeded;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    LocalDateTime time;
    Long duration;
}
